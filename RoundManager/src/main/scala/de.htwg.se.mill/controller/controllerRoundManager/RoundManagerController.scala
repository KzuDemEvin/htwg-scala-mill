package de.htwg.se.mill.controller.controllerRoundManager

import de.htwg.se.mill.model._
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.javadsl.settings.ConnectionPoolSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethod, HttpRequest, HttpResponse}
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.RoundManagerModule
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.roundManagerComponent.RoundManager
import de.htwg.se.mill.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class RoundManagerController @Inject()(var field: FieldInterface) extends RoundManagerControllerInterface {
  var mgr: RoundManager = RoundManager(field)
  val injector: Injector = Guice.createInjector(new RoundManagerModule)
  private lazy val undoManager: UndoManager = new UndoManager
  lazy val playerHttpServer: String = sys.env.getOrElse("PLAYERHTTPSERVER", "localhost:8081")

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  doStep()

  def handleClick(row: Int, col: Int): String = {
    print(s"HandleClick at position ($row, $col) called!\t")
    mgr = mgr.handleClick(row, col)
    print(s"Round: ${mgr.roundCounter} \tMill: ${mgr.field.millState}\n")
    doStep()
    mgr.updateToJson()
  }

  def doStep(): String = {
    undoManager.doStep(mgr.copy())
    fieldAsJson()
  }

  def undo(): String = {
    print(s"Undo called!\n")
    mgr = undoManager.undoStep() match {
      case Some(roundManager) => roundManager.copy()
      case None => mgr.copy()
    }
    fieldAsJson()
  }

  def redo(): String = {
    print(s"Redo called!\n")
    mgr = undoManager.redoStep() match {
      case Some(roundManager) => roundManager.copy()
      case None => mgr.copy()
    }
    fieldAsJson()
  }

  def setField(fieldAsString: String): String = {
    print(s"Setting field called!\n")
    val field: FieldInterface = jsonToField(fieldAsString)
    mgr = mgr.copy(field = field, roundCounter = field.savedRoundCounter, player1Mode = ModeState.whichState(field.player1Mode), player2Mode = ModeState.whichState(field.player2Mode))
    fieldAsJson()
  }

  def createEmptyField(size: Int): String = {
    print(s"Creating empty field of size $size!\n")
    mgr = mgr.copy(winner = 0, roundCounter = 0, field = injector.instance[FieldInterface](Names.named("normal"))).modeChoice()
    fieldAsJson()
  }

  def createRandomField(size: Int): String = {
    print(s"Creating random field of size $size!\n")
    mgr = mgr.copy(winner = 0, roundCounter = mgr.borderToMoveMode, field = injector.instance[FieldInterface](Names.named("random"))).modeChoice()
    fieldAsJson()
  }

  def turn(): String = {
    print(s"Turn called!\n")
    new Gson().toJson(if (mgr.blackTurn()) "Black" else "White")
  }

  def roundCounter(): String = {
    print(s"RoundCounter called!\n")
    new Gson().toJson(mgr.roundCounter)
  }

  def winner(): String = {
    print(s"Winner called!\n")
    new Gson().toJson(mgr.winner)
  }

  def winnerText(): Future[String] = {
    print(s"WinnerText called!\n")
    val player: String = blockRequest(s"http://$playerHttpServer/player/name?number=${winner + 1}", GET, s"Player ${(mgr.winner % 2) + 1}")
    val winnerText: String = {
      mgr.winner match {
        case 2 => " wins! (white)"
        case 1 => " wins! (black)"
        case _ => "No Winner"
      }
    }
    new Gson().toJson(player + winnerText)
  }

  def cell(row: Int, col: Int): Cell = {
    print(s"Cell at position ($row, $col) called!\n")
    mgr.field.cell(row, col)
  }

  def isSet(row: Int, col: Int): String = {
    print(s"IsSet at position ($row, $col) called!\n")
    new Gson().toJson(cell(row, col).isSet)
  }

  def color(row: Int, col: Int): String = {
    print(s"Color at position ($row, $col) called!\n")
    new Gson().toJson(cell(row, col).content.color match {
      case Color.black => 1
      case Color.white => 0
      case Color.noColor => -1
    })
  }

  def possiblePosition(row: Int, col: Int): String = new Gson().toJson(mgr.field.possiblePosition(row, col))

  def millState(): String = new Gson().toJson(mgr.field.millState)

  def fieldAsJson(): String = {
    Json.prettyPrint(Json.obj(
      "field" -> Json.obj(
        "roundCounter" -> JsNumber(mgr.roundCounter),
        "player1Mode" -> JsString(mgr.player1Mode.handle),
        "player2Mode" -> JsString(mgr.player2Mode.handle),
        "cells" -> Json.toJson(
          for {
            row <- 0 until mgr.field.size
            col <- 0 until mgr.field.size
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "color" -> Json.toJson(mgr.field.cell(row, col).content.color)
            )
          }
        )
      )
    ))
  }

  def jsonToField(fieldInJson: String): FieldInterface = {
    val source: String = fieldInJson
    val json: JsValue = Json.parse(source)
    val roundCounter = (json \ "field" \ "roundCounter").get.toString.toInt
    val player1Mode = (json \ "field" \ "player1Mode").get.toString.replaceAll("\"", "")
    val player2Mode = (json \ "field" \ "player2Mode").get.toString.replaceAll("\"", "")
    val injector = Guice.createInjector(new RoundManagerModule)
    var newField = injector.instance[FieldInterface](Names.named("normal"))
    for (index <- 0 until newField.size * newField.size) {
      val row = (json \\ "row") (index).as[Int]
      val col = (json \\ "col") (index).as[Int]
      val color = (json \\ "color") (index).toString().replaceAll("\"", "")
      newField = color match {
        case "white" => newField.set(row, col, Cell("cw"))._1
        case "black" => newField.set(row, col, Cell("cb"))._1
        case "noColor" => newField.set(row, col, Cell("ce"))._1
      }
    }
    newField = newField.setRoundCounter(roundCounter).setPlayer1Mode(player1Mode).setPlayer2Mode(player2Mode)
    newField
  }

  def fieldAsHtml(): String = mgr.field.toHtml

  def fieldAsString(): String = mgr.field.toString

  private def sendRequest(uri: String, method: HttpMethod, errMsg: String = "Something went wrong."): Future[HttpResponse] = {
    Http().singleRequest(HttpRequest(method = method, uri = uri))
  }

  private def unmarshal(value: HttpResponse): Future[String] = {
    Unmarshal(value.entity).to[String]
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
}