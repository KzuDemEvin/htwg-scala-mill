package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.mill.controller.controllerComponent._
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller extends ControllerInterface with Publisher {
  // private val undoManager = new UndoManager
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  var gameState: String = GameState.handle(NewState())
  // var cachedField: Option[JsValue] = None
  var cachedFieldAsHtml: String = ""


  val playerHttpServer: String = sys.env.getOrElse("PLAYERHTTPSERVER", "localhost:8081")
  val fileIOHttpServer: String = sys.env.getOrElse("FILEIOHTTPSERVER", "localhost:8082")
  val roundManagerHttpServer: String = sys.env.getOrElse("ROUNDMANAGERHTTPSERVER", "localhost:8083")

  def createPlayer(name: String, number: Int = 1): String = {
    asyncRequest(s"http://${playerHttpServer}/player?number=$number&name=$name", POST)({
      case Some(_) =>
    })
    ""
  }

  def createEmptyField(size: Int): Unit = {
    asyncRequest(s"http://${roundManagerHttpServer}/field/createField?size=$size", POST)({
      case Some(_) =>
        gameState = GameState.handle(NewState())
        publish(new FieldChanged)
    })
  }

  def createEmptyFieldSync(size: Int): String = {
    val field: String = blockRequest(s"http://${roundManagerHttpServer}/field/createField?size=$size", POST)
    gameState = GameState.handle(NewState())
    publish(new FieldChanged)
    field
  }

  def createRandomField(size: Int): Unit = {
    asyncRequest(s"http://${roundManagerHttpServer}/field/createRandomField?size=$size", POST)({
      case Some(_) =>
        gameState = GameState.handle(RandomState())
        publish(new FieldChanged)
    })
  }

  def createRandomFieldSync(size: Int): String = {
    val field: String = blockRequest(s"http://${roundManagerHttpServer}/field/createRandomField?size=$size", POST)
    publish(new FieldChanged)
    gameState = GameState.handle(RandomState())
    field
  }

  def handleClick(row: Int, col: Int)(oncomplete: Option[String] => Unit = {
    case Some(_) =>
    case None =>
  }): Unit =
    asyncRequest(s"http://${roundManagerHttpServer}/handleClick?row=$row&col=$col", POST)(value => {
      oncomplete(value)
      turn()
      value match {
        case Some(changeType) =>
          changeType.toInt match {
            case 0 => publish(new StateChanged)
            case 1 => publish(new CellChanged)
            case _ => publish(new FieldChanged)
          }
      }
    })

  def handleClickSync(row: Int, col: Int): String = {
    val field = blockRequest(s"http://${roundManagerHttpServer}/handleClick?row=$row&col=$col", POST)
    turn()
    publish(new CellChanged)
    field
  }

  def undo(): Unit = {
    asyncRequest(s"http://${roundManagerHttpServer}/undo", POST)(_ => {
      print("Hello!")
      turn()
      publish(new FieldChanged)
    })
  }

  def redo(): Unit = {
    asyncRequest(s"http://${roundManagerHttpServer}/redo", POST)(_ => {
      turn()
      publish(new FieldChanged)
    })
  }

  def save(): Unit = {
    val field: String = blockRequest(s"http://${roundManagerHttpServer}/field/json", GET)
    Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = s"http://${fileIOHttpServer}/json", entity = Json.prettyPrint(Json.parse(field))))
    gameState = GameState.handle(SaveState())
    publish(new CellChanged)
  }

  def load(): Unit = {
    gameState = GameState.handle(LoadState())
    val field: String = blockRequest(s"http://${fileIOHttpServer}/json", GET)
    Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = s"http://${roundManagerHttpServer}/field/setField", entity = Json.prettyPrint(Json.parse(field))))
    gameState = GameState.handle(LoadState())
    publish(new FieldChanged)
  }

  def isSet(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/isSet?row=$row&col=$col")(oncomplete)

  def color(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/color?row=$row&col=$col")(oncomplete)

  def possiblePosition(row: Int, col: Int): Boolean = {
    val horizontalCells = List((0, 1), (0, 2), (0, 4), (0, 5), (1, 2), (1, 4), (5, 2), (5, 4), (6, 1), (6, 2), (6, 4), (6, 5))
    val verticalCells = List((1, 0), (1, 6), (2, 0), (2, 1), (2, 5), (2, 6), (4, 0), (4, 1), (4, 5), (4, 6), (5, 0), (5, 6))
    !horizontalCells.contains((row, col)) && !verticalCells.contains((row, col))
  }

  def getMillState(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/millState")(oncomplete)

  def turn(): Unit = asyncRequest(s"http://${roundManagerHttpServer}/turn")({ case Some(state) => gameState = GameState.whichState(state).handle })

  def fieldsize: Int = 7

  def fieldToHtml(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/html")(oncomplete)

  def fieldToHtmlSync: String = blockRequest(s"http://${roundManagerHttpServer}/field/html", GET)

  def fieldToString(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/string")(oncomplete)

  def fieldToJson(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/field/json")(oncomplete)

  def getRoundCounter(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/roundCounter")(oncomplete)

  def getWinner(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/winner")(oncomplete)

  def getWinnerText(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://${roundManagerHttpServer}/winnerText")(oncomplete)

  private def sendRequest(uri: String, method: HttpMethod, errMsg: String = "Something went wrong."): Future[HttpResponse] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    Http().singleRequest(HttpRequest(method = method, uri = uri))
  }

  private def asyncRequest(uri: String, method: HttpMethod = HttpMethods.GET, errMsg: String = "Async Request failed.")(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(uri, method).onComplete({
      case Failure(_) => sys.error(errMsg)
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  private def blockRequest(uri: String, method: HttpMethod, failed: String = ""): String = {
    block(sendRequest(uri, method)) match {
      case Some(response) =>
        block(unmarshal(response)) match {
          case Some(unpackedString) => unpackedString
          case None => failed
        }
      case None => failed
    }
  }

  private def block[T](future: Future[T]): Option[T] = {
    Await.ready(future, Duration.Inf).value.get match {
      case Success(s) => Some(s)
      case Failure(_) => None
    }
  }

  private def unmarshalAsync(value: HttpResponse)(oncomplete: Option[String] => Unit): Unit = {
    unmarshal(value).onComplete {
      case Failure(_) =>
        sys.error("Unmarshalling went wrong")
        oncomplete(None)
      case Success(s) =>
        oncomplete(Some(s.stripPrefix(s"${'"'}").stripSuffix(s"${'"'}")))
    }
  }

  private def unmarshal(value: HttpResponse): Future[String] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    Unmarshal(value.entity).to[String]
  }
}