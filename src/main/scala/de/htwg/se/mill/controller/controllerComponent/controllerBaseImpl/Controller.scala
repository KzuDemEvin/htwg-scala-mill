package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.mill.controller.controllerComponent._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller extends ControllerInterface with Publisher {
  // private val undoManager = new UndoManager
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  var gameState: String = GameState.handle(NewState())
  var cachedField: Option[JsValue] = None
  var cachedFieldAsHtml: String = ""

  def createPlayer(name: String, number: Int = 1): String = {
    /* sendRequest(s"http://localhost:8082/player?number=${number}&name=${name}", POST, s"Creating player ${name} went wrong.") match {
      case Some(player) => player
      case None => ""
    } */
    ""
  }

  def createEmptyField(size: Int): Unit = {
    sendRequest(s"http://localhost:8083/field/createField?size=${size}", POST, s"Creating field of size ${size} went wrong.").onComplete({
      case Failure(_) => sys.error("createEmptyField failed.")
      case Success(value) => unmarshalAsync(value)({
        case Some(field) =>
          cachedField = Some(Json.parse(field))
          gameState = GameState.handle(NewState())
          publish(new FieldChanged)
        case None =>
      })
    })
  }

  def createRandomField(size: Int): Unit = {
    sendRequest(s"http://localhost:8083/field/createRandomField?size=${size}", POST, s"Creating random field of size ${size} went wrong.").onComplete({
      case Failure(_) => sys.error("createRandomField failed.")
      case Success(value) => unmarshalAsync(value)({
        case Some(field) =>
          gameState = GameState.handle(RandomState())
          cachedField = Some(Json.parse(field))
          publish(new FieldChanged)
        case None =>
      })
    })
  }

  def handleClick(row: Int, col: Int)(oncomplete: Option[String] => Unit = {
    case Some(_) => {}
    case None => {}
  }): Unit = {
    sendRequest(s"http://localhost:8083/handleClick?row=${row}&col=${col}", POST).onComplete({
      case Failure(_) => sys.error("handleClick failed.")
      case Success(value) => unmarshalAsync(value)(value => {
        oncomplete(value)
        turn
        publish(new CellChanged)
      })
    })
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

  def isSet(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/isSet?row=${row}&col=${col}", GET).onComplete({
      case Failure(_) => sys.error("isSet failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def color(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/color?row=${row}&col=${col}", GET).onComplete({
      case Failure(_) => sys.error("color failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def possiblePosition(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/possiblePosition?row=${row}&col=${col}", GET).onComplete({
      case Failure(_) => sys.error("possiblePosition failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def getMillState(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/millState", GET).onComplete({
      case Failure(_) => sys.error("getMillState failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def turn: Unit = {
    sendRequest(s"http://localhost:8083/turn", GET).onComplete({
      case Failure(_) => sys.error("getMillState failed.")
      case Success(value) => unmarshalAsync(value)({
        case Some(state) => gameState = GameState.whichState(state).handle
      })
    })
  }

  def fieldsize: Int = 7

  def fieldToHtml(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/html", GET).onComplete({
      case Failure(_) => sys.error("fieldToHtml failed.")
      case Success(value) => unmarshalAsync(value)(value => {
        cachedFieldAsHtml = value match {
          case Some(field) => field
          case None => ""
        }
        oncomplete(value)
      })
    })
  }

  def fieldToHtmlSync: String = {
    block(sendRequest(s"http://localhost:8083/field/html", GET)) match {
      case Some(request) =>
        block(unmarshal(request)) match {
          case Some(field) => field
          case None => ""
        }
      case None => ""
    }
  }

  def fieldToString(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/string", GET).onComplete({
      case Failure(_) => sys.error("fieldToString failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def fieldToJson(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/json", GET).onComplete({
      case Failure(_) => sys.error("fieldToJson failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def getRoundCounter(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/roundCounter", GET).onComplete({
      case Failure(_) => sys.error("RoundCounter failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def getWinner(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/winner", GET).onComplete({
      case Failure(_) => sys.error("Possible Position failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  def getWinnerText(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/winnerText", GET).onComplete({
      case Failure(_) => sys.error("Possible Position failed.")
      case Success(value) => unmarshalAsync(value)(oncomplete)
    })
  }

  /* private def futureHandler[T1, T2](future: Future[T1])(onSuccess: Try[T1] => Future[T2]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    val executionContext: ExecutionContextExecutor = system.executionContext
    future.onComplete(onSuccess)(executionContext)
  }

  private def sendRequestFuture[T](uri: String, method: HttpMethod)(onSuccess: Try[HttpResponse] => Future[T]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    futureHandler[HttpResponse, T](Http().singleRequest(HttpRequest(method = method, uri = uri)))(onSuccess)
  } */

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

  private def block[T](future: Future[T]): Option[T] = {
    Await.ready(future, Duration.Inf).value.get match {
      case Success(s) => Some(s)
      case Failure(_) => None
    }
  }

  private def sendRequest(uri: String, method: HttpMethod, errMsg: String = "Something went wrong."): Future[HttpResponse] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    Http().singleRequest(HttpRequest(method = method, uri = uri))
  }
}