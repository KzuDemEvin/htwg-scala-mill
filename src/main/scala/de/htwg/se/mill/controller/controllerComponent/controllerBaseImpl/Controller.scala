package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.htwg.se.mill.controller.controllerComponent._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.swing.Publisher
import scala.util.{Failure, Success, Try}

class Controller extends ControllerInterface with Publisher {
  // private val undoManager = new UndoManager
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext
  var gameState: String = GameState.handle(NewState())
  var cachedPossiblePositions: scala.collection.mutable.Map[(Int, Int), Boolean] = scala.collection.mutable.Map[(Int, Int), Boolean]()
  var cachedField: Option[JsValue] = None

  def createPlayer(name: String, number: Int = 1): String = {
    /* sendRequest(s"http://localhost:8082/player?number=${number}&name=${name}", POST, s"Creating player ${name} went wrong.") match {
      case Some(player) => player
      case None => ""
    } */
    ""
  }

  def createEmptyField(size: Int): Unit = {
    /*sendRequest(s"http://localhost:8083/field/createField?size=${size}", POST, s"Creating field of size ${size} went wrong.") match {
      case Some(field) =>
        publish(new DataArrived)
        cachedField = Some(Json.parse(field))
      case None => cachedField = None
    }
    gameState = GameState.handle(NewState())
    publish(new CellChanged)*/
  }

  def createRandomField(size: Int): Unit = {
    /*
    sendRequest(s"http://localhost:8083/field/createRandomField?size=${size}", POST, s"Creating random field of size ${size} went wrong.") match {
      case Some(field) =>
        publish(new DataArrived)
        cachedField = Some(Json.parse(field))
      case None => cachedField = None
    }
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
    */
  }

  def handleClick(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/handleClick?row=${row}&col=${col}", POST).onComplete({
      case Failure(_) => sys.error("handleClick failed.")
      case Success(value) => unmarshal(value)(oncomplete)
    })
  }

  def turn(): String = {
    /*
    sendRequest(s"http://localhost:8083/turn", GET) match {
      case Some(color) =>
        publish(new DataArrived)
        color
      case None => ""
    }

     */
    ""
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
      case Success(value) => unmarshal(value)(oncomplete)
    })
  }

  def color(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/color?row=${row}&col=${col}", GET).onComplete({
      case Failure(_) => sys.error("Possible Position failed.")
      case Success(value) => unmarshal(value)(oncomplete)
    })
  }

  def possiblePosition(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = {
    sendRequest(s"http://localhost:8083/field/possiblePosition?row=${row}&col=${col}", GET).onComplete({
      case Failure(_) => sys.error("Possible Position failed.")
      case Success(value) => unmarshal(value)(oncomplete)
    })
  }

  def getMillState: String = {
    /*
    sendRequest(s"http://localhost:8083/field/millState", GET) match {
      case Some(millState) => millState
      case None => ""
    }*/
    ""
  }

  def fieldsize: Int = 7

  def fieldToHtml: String = {
    /*
    sendRequest(s"http://localhost:8083/field/html", GET) match {
      case Some(fieldAsHtml) => fieldAsHtml
      case None => ""
    } */
    ""
  }

  def fieldToString: String = {
    /*
    sendRequest(s"http://localhost:8083/field/string", GET) match {
      case Some(fieldAsString) => fieldAsString
      case None => ""
    } */
    ""
  }

  def getRoundCounter: Int = {
    /*
    sendRequest(s"http://localhost:8083/roundCounter", GET) match {
      case Some(counter) => counter.toInt
      case None => 0
    }*/ 0
  }

  override def getWinner: Int = {
    /*
    sendRequest(s"http://localhost:8083/winner", GET) match {
      case Some(winner) => winner.toInt
      case None => -1
    }*/ 0
  }

  override def getWinnerText: String = {
    /*
    sendRequest(s"http://localhost:8083/winnerText", GET) match {
      case Some(winnerText) =>
        publish(new DataArrived)
        winnerText
      case None => ""
    }*/ ""
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

  private def unmarshal(value: HttpResponse)(oncomplete: Option[String] => Unit): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    Unmarshal(value.entity).to[String].onComplete {
      case Failure(_) =>
        sys.error("Unmarshalling went wrong")
        oncomplete(None)
      case Success(s) =>
        oncomplete(Some(s))
    }
  }


  private def sendRequest(uri: String, method: HttpMethod, errMsg: String = "Something went wrong."): Future[HttpResponse] = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(method = method, uri = uri))
    /* responseFuture.onComplete {
      case Failure(_) => sys.error(errMsg)
      case Success(value) =>
        print(value)
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Unmarshalling went wrong")
          case Success(s) =>
            print("Yeah")
            publish(new DataArrived)
            unpacked = Some(s)
        }
    }*/
    responseFuture
  }
}