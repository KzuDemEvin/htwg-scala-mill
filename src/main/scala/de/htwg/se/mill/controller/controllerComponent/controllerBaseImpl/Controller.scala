package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Injector}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}

import scala.concurrent.Future
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller extends ControllerInterface with Publisher {
  private val undoManager = new UndoManager
  var gameState: String = GameState.handle(NewState())
  val injector: Injector = Guice.createInjector(new MillModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]


  def createPlayer(name: String, number: Int = 1): Player = {

    implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = s"http://localhost:8082/player?number=${number}&name=${name}"))

    responseFuture.onComplete {
      case Failure(_) => sys.error(s"Creating player ${name} went wrong")
      case Success(value) => {
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Unmarshalling went wrong")
          case Success(result) => {
            // TODO
          }
        }
      }
    }

    val player: Player = Player(name)
    player
  }

  def createEmptyField(size: Int): Unit = {
    // mgr.copy(winner = 0, roundCounter = 0, field = injector.instance[FieldInterface](Names.named("normal"))).modeChoice()
    gameState = GameState.handle(NewState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    // mgr.copy(winner = 0, roundCounter = mgr.borderToMoveMode, field = injector.instance[FieldInterface](Names.named("random"))).modeChoice()
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def handleClick(row: Int, col: Int): Unit = {
    // mgr.handleClick(row, col)
    gameState = GameState.handle(if (mgr.blackTurn()) BlackTurnState() else WhiteTurnState())
    publish(new CellChanged)
  }


  def undo: Unit = {
    undoManager.undoStep()
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep()
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def checkMill(row: Int, col: Int): String = {
    mgr = this.mgr.copy(field = mgr.field.checkMill(row, col))
    mgr.field.millState
  }

  def save: Unit = {
    // TODO
    val field = mgr.setRoundCounter(mgr.roundCounter)
      .setPlayer1Mode(mgr.player1Mode)
      .setPlayer2Mode(mgr.player2Mode)

    implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext = system.executionContext

    val fieldJsonString = Json.prettyPrint(fieldToJson(field))
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://localhost:8082/json", entity = fieldJsonString))

    gameState = GameState.handle(SaveState())
    publish(new CellChanged)
  }

  def fieldToJson(field: FieldInterface): JsValue = {
    Json.obj(
      "field" -> Json.obj(
        "roundCounter" -> JsNumber(field.savedRoundCounter),
        "player1Mode" -> JsString(field.player1Mode),
        "player2Mode" -> JsString(field.player2Mode),
        "cells" -> Json.toJson(
          for {
            row <- 0 until field.size
            col <- 0 until field.size
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "color" -> Json.toJson(field.cell(row, col).content.color)
            )
          }
        )
      )
    )
  }

  def load: Unit = {
    val field = fileIo.load(None)
    mgr = this.mgr.copy(field = field, roundCounter = field.savedRoundCounter)
      .setPlayerMode(field.player1Mode)
      .setPlayerMode(field.player2Mode, 2)
    gameState = GameState.handle(LoadState())
    publish(new CellChanged)
    implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext = system.executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8082/json"))

    responseFuture.onComplete {
      case Failure(_) => sys.error("Loading game went wrong")
      case Success(value) => {
        Unmarshal(value.entity).to[String].onComplete {
          case Failure(_) => sys.error("Unmarshalling went wrong")
          case Success(result) => {
            field = jsonToField(result)
            // TODO
            mgr = this.mgr.copy(roundCounter = field.savedRoundCounter)
              .setPlayerMode(field.player1Mode)
              .setPlayerMode(field.player2Mode, 2)
            gameState = GameState.handle(LoadState())
            publish(new CellChanged)
          }
        }
      }
    }


  }

  def jsonToField(fieldInJson: String): FieldInterface = {
    val source: String = fieldInJson
    val json: JsValue = Json.parse(source)
    val roundCounter = (json \ "field" \ "roundCounter").get.toString.toInt
    val player1Mode = (json \ "field" \ "player1Mode").get.toString.replaceAll("\"", "")
    val player2Mode = (json \ "field" \ "player2Mode").get.toString.replaceAll("\"", "")
    val injector = Guice.createInjector(new MillModule)
    var field = injector.instance[FieldInterface](Names.named("normal"))
    for (index <- 0 until field.size * field.size) {
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      val color = (json \\ "color")(index).toString().replaceAll("\"", "")
      field = color match {
        case "white" => field.set(row, col, Cell("cw"))
        case "black" => field.set(row, col, Cell("cb"))
        case "noColor" => field.set(row, col, Cell("ce"))
      }
    }
    field.setRoundCounter(roundCounter)
      .setPlayer1Mode(player1Mode)
      .setPlayer2Mode(player2Mode)
    field
  }

  def cell(row: Int, col: Int): String = mgr.field.cell(row, col)
  def isSet(row: Int, col: Int): Boolean = mgr.field.cell(row, col).isSet
  def possiblePosition(row: Int, col: Int): Boolean = mgr.field.possiblePosition(row, col)
  def getMillState: String = mgr.field.millState
  def fieldsize: Int = mgr.field.size
  def fieldToHtml: String = mgr.field.toHtml
  def fieldToString: String = mgr.field.toString
  def getRoundManager: RoundManager = mgr
  def getRoundCounter: Int = mgr.roundCounter
}