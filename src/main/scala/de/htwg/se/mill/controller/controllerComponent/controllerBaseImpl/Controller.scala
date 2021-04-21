package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.mill.controller.controllerComponent._

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

  def createPlayer(name: String, number: Int = 1): String = {
    /* sendRequest(s"http://localhost:8082/player?number=${number}&name=${name}", POST, s"Creating player ${name} went wrong.") match {
      case Some(player) => player
      case None => ""
    } */
    ""
  }

  def createEmptyField(size: Int): Unit = {
    asyncRequest(s"http://localhost:8083/field/createField?size=${size}", POST)({
      case Some(_) =>
        gameState = GameState.handle(NewState())
        publish(new FieldChanged)
    })
  }

  def createEmptyFieldSync(size: Int): String = {
    val field: String = blockRequest(s"http://localhost:8083/field/createField?size=${size}", POST)
    gameState = GameState.handle(NewState())
    publish(new FieldChanged)
    field
  }

  def createRandomField(size: Int): Unit = {
    asyncRequest(s"http://localhost:8083/field/createRandomField?size=${size}", POST)({
      case Some(_) =>
        gameState = GameState.handle(RandomState())
        publish(new FieldChanged)
    })
  }

  def createRandomFieldSync(size: Int): String = {
    val field: String = blockRequest(s"http://localhost:8083/field/createRandomField?size=${size}", POST)
    publish(new FieldChanged)
    gameState = GameState.handle(RandomState())
    field
  }

  def handleClick(row: Int, col: Int)(oncomplete: Option[String] => Unit = {case Some(_) => {} case None => {}}): Unit =
    asyncRequest(s"http://localhost:8083/handleClick?row=${row}&col=${col}", POST)(value => {
      oncomplete(value)
      turn
      publish(new CellChanged)
  })

  def handleClickSync(row: Int, col: Int): String = {
    val field = blockRequest(s"http://localhost:8083/handleClick?row=${row}&col=${col}", POST)
    turn
    publish(new CellChanged)
    field
  }

  def undo: Unit = {
    /*
    cachedField = sendRequest(s"http://localhost:8083/undo", POST, "Undo failed.") match {
      case Some(field) => Some(Json.parse(field))
      case None => None
    }
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
     */
  }

  def redo: Unit = {
    /*
    cachedField = sendRequest(s"http://localhost:8083/redo", POST, "Redo failed.") match {
      case Some(field) => Some(Json.parse(field))
      case None => None
    }
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)

     */
  }

  def save: Unit = {
    /*
    cachedField = sendRequest("http://localhost:8083/field", GET, "Getting field went wrong.") match {
      case Some(fieldAsString) => Some(Json.parse(fieldAsString))
      case None => None
    }

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    cachedField match {
      case Some(field) =>
        Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://localhost:8082/json", entity = Json.prettyPrint(field)))
      case None =>
    }
    gameState = GameState.handle(SaveState())
    publish(new CellChanged)

     */
  }

  def load: Unit = {
    /*
    gameState = GameState.handle(LoadState())
    cachedField = sendRequest("http://localhost:8082/json", GET, "Loading game went wrong") match {
      case Some(fieldAsString) =>
        Some(Json.parse(fieldAsString))
      case None => None
    }

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    // updating field from RoundManager
    cachedField match {
      case Some(field) =>
        Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://localhost:8083/setField", entity = Json.prettyPrint(field)))
      case None =>
    }

    gameState = GameState.handle(LoadState())
    publish(new CellChanged)

     */
  }

  def isSet(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/isSet?row=${row}&col=${col}")(oncomplete)

  def color(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/color?row=${row}&col=${col}")(oncomplete)

  def possiblePosition(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/possiblePosition?row=${row}&col=${col}")(oncomplete)

  def getMillState(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/millState")(oncomplete)

  def turn: Unit = asyncRequest(s"http://localhost:8083/turn")({ case Some(state) => gameState = GameState.whichState(state).handle })

  def fieldsize: Int = 7

  def fieldToHtml(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/html")(oncomplete)

  def fieldToHtmlSync: String = blockRequest(s"http://localhost:8083/field/html", GET)

  def fieldToString(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/string")(oncomplete)

  def fieldToJson(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/field/json")(oncomplete)

  def getRoundCounter(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/roundCounter")(oncomplete)

  def getWinner(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/winner")(oncomplete)

  def getWinnerText(oncomplete: Option[String] => Unit): Unit = asyncRequest(s"http://localhost:8083/winnerText")(oncomplete)

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

  /* NONBLOCKING REQUEST */

  private def asyncRequest(uri: String, method: HttpMethod = HttpMethods.GET, errMsg: String = "Async Request failed.")(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(uri, method).onComplete({
      case Failure(_) => sys.error(errMsg)
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  /* BLOCKING REQUEST*/

  private def block[T](future: Future[T]): Option[T] = {
    Await.ready(future, Duration.Inf).value.get match {
      case Success(s) => Some(s)
      case Failure(_) => None
    }
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

  private def sendRequest(uri: String, method: HttpMethod, errMsg: String = "Something went wrong."): Future[HttpResponse] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    Http().singleRequest(HttpRequest(method = method, uri = uri))
  }
}