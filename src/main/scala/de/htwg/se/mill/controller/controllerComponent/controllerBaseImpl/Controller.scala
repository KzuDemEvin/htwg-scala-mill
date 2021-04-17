package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerBaseImpl.RoundManager
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import de.htwg.se.mill.util.{CommandTrait, UndoManager}
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}

import scala.concurrent.Future
import scala.swing.Publisher
import scala.util.{Failure, Success}

class Controller @Inject()(var field: FieldInterface) extends ControllerInterface with Publisher {
  private val undoManager = new UndoManager
  var mgr: RoundManager = new RoundManager()
  var tmpCell: (Int, Int) = (0, 0)
  var setCounter = 0
  var moveCounter = 0
  var flyCounter = 0
  var gameState: String = GameState.handle(NewState())
  var millState: String = MillState.handle(NoMillState())
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
    mgr = this.mgr.copy().setPlayer(player, number)
    player
  }

  def createEmptyField(size: Int): Unit = {
    resetCounters()
    field = injector.instance[FieldInterface](Names.named("normal"))
    mgr = this.mgr.copy(winner = 0, roundCounter = 0)
      .modeChoice(field)
    gameState = GameState.handle(NewState())
    millState = MillState.handle(NoMillState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    resetCounters()
    field = injector.instance[FieldInterface](Names.named("random"))
    mgr = this.mgr
      .copy(winner = 0, roundCounter = this.mgr.borderToMoveMode)
      .modeChoice(field)
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def getRoundCounter: Int = mgr.roundCounter

  def handleClick(row: Int, column: Int): Unit = {
    gameState = GameState.handle(if (mgr.blackTurn()) BlackTurnState() else WhiteTurnState())

    selectDriveCommand() match {
      case SetModeState() => setCounter = handleSet(row, column, setCounter)
      case MoveModeState() => moveCounter = handleMoveAndFly(row, column, moveCounter, MoveModeState())
      case FlyModeState() => flyCounter = handleMoveAndFly(row, column, flyCounter, FlyModeState())
    }
    publish(new CellChanged)
  }

  private def handleSet(row: Int, column: Int, counter: Int): Int = {
    var cnt: Int = counter
    if (cnt >= 1) {
      if (removeStone(row, column)) {
        cnt = 0
        updateRoundCounter(mgr.roundCounter + 1)
      } else {
        cnt += 1
      }
    } else {
      handleSetHelper(row, column)
      checkMill(row, column) match {
        case "White Mill" | "Black Mill" => cnt += 1
        case "No Mill" => cnt = 0
          updateRoundCounter(mgr.roundCounter + 1)
      }
    }
    mgr = this.mgr.modeChoice(field)
    cnt
  }

  private def handleMoveAndFly(row: Int, column: Int, counter: Int, mode: ModeState): Int = {
    var cnt = counter + 1
    if (cnt == 2) {
      handleMoveAndFlyHelper(tmpCell._1, tmpCell._2, row, column,
        if (mode == MoveModeState()) {
          new MoveCommand(tmpCell._1, tmpCell._2, row, column, this)
        } else {
          new FlyCommand(tmpCell._1, tmpCell._2, row, column, this)
        })
      checkMill(row, column) match {
        case "White Mill" | "Black Mill" => cnt += 1
        case "No Mill" => cnt = 0
          updateRoundCounter(mgr.roundCounter + 1)
      }
    } else if (cnt >= 4) {
      if (removeStone(row, column)) {
        cnt = 0
        updateRoundCounter(mgr.roundCounter + 1)
      } else {
        checkWinner(row, column)
      }
    } else {
      tmpCell = (row, column)
    }
    mgr = mgr.modeChoice(field)
    cnt
  }

  private def handleMoveAndFlyHelper(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int, command: CommandTrait): Unit = {
    if ((command.isInstanceOf[FlyCommand] && field.available(rowNew, colNew)) ||
      (command.isInstanceOf[MoveCommand] && field.available(rowNew, colNew) && isNeigbour(rowOld, colOld, rowNew, colNew))
    ) {
      val cellColor = cell(rowOld, colOld).content.color
      if (cellColor == Color.black || cellColor == Color.white) {
        undoManager.doStep(command)
      } else {
        updateRoundCounter(mgr.roundCounter - 1)
      }
    } else {
      updateRoundCounter(mgr.roundCounter - 1)
    }
    publish(new CellChanged)
  }

  def selectDriveCommand(): ModeState = mgr.selectDriveCommand()

  private def handleSetHelper(row: Int, col: Int): Unit = {
    if (field.available(row, col)) {
      undoManager.doStep(new SetCommand(row, col, if (mgr.blackTurn()) Cell("cb") else Cell("cw"), this))
      gameState = GameState.handle(if (mgr.blackTurn()) WhiteTurnState() else BlackTurnState())
    } else {
      updateRoundCounter(mgr.roundCounter - 1)
    }
    publish(new CellChanged)
  }

  def undo: Unit = {
    undoManager.undoStep()
    step()
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep()
    step()
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  private def step(): Unit = {
    if (mgr.roundCounter > 0) {
      updateRoundCounter(mgr.roundCounter - 1)
      resetCounters()
    }
  }

  def checkMill(row: Int, col: Int): String = {
    millState = field.checkMill(row, col) match {
      case 1 => MillState.handle(BlackMillState())
      case 2 => MillState.handle(WhiteMillState())
      case _ => MillState.handle(NoMillState())
    }
    millState
  }

  def removeStone(row: Int, col: Int): Boolean = {
    val r = stoneHasOtherColor(row, col, if (mgr.blackTurn()) Color.white else Color.black)
    mgr = this.mgr.copy().modeChoice(field)
    publish(new CellChanged)
    r
  }

  private def stoneHasOtherColor(row: Int, col: Int, color: Color.Value): Boolean = {
    var r = (field, false)
    if (cell(row, col).content.color == color) {
      r = field.removeStone(row, col)
      field = r._1
    }
    r._2
  }

  def checkWinner(row: Int, column: Int): Unit = {
    if (mgr.player1.mode == ModeState.handle(FlyModeState()) && mgr.player2.mode == ModeState.handle(FlyModeState())) {
      val winner = checkMill(row, column) match {
        case "White Mill" => 2
        case "Black Mill" => 1
        case "No Mill" => 0
      }
      mgr = this.mgr.copy(
        winner = winner,
        winnerText = this.mgr.copy().handleWinnerText(winner)
      )
    }
  }

  def save: Unit = {
    // TODO
    field = field.setRoundCounter(mgr.roundCounter)
      .setPlayer1Mode(mgr.player1.mode)
      .setPlayer1Name(mgr.player1.name)
      .setPlayer2Mode(mgr.player2.mode)
      .setPlayer2Name(mgr.player2.mode)

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
              .setPlayer(mgr.player1.changeMode(field.player1Mode))
              .setPlayer(mgr.player2.changeMode(field.player2Mode), 2)
            gameState = GameState.handle(LoadState())
            publish(new CellChanged)
          }
        }
      }
    }


  }

  def jsonToField(fieldInJson: String): Field = {
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

  def resetCounters(): Unit = {
    setCounter = 0
    moveCounter = 0
    flyCounter = 0
  }

  private def updateRoundCounter(roundCounter: Int): RoundManager = {
    mgr = this.mgr.copy(roundCounter = roundCounter)
    mgr
  }

  def fieldToHtml: String = field.toHtml
  def cell(row: Int, col: Int): Cell = field.cell(row, col)
  def isSet(row: Int, col: Int): Boolean = field.cell(row, col).isSet
  def available(row: Int, col: Int): Boolean = field.available(row, col)
  def possiblePosition(row: Int, col: Int): Boolean = field.possiblePosition(row, col)
  def placedStones(): Int = field.placedStones()
  def placedWhiteStones(): Int = field.placedWhiteStones()
  def placedBlackStones(): Int = field.placedBlackStones()
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Boolean =
    field.isNeighbour(rowOld, colOld, rowNew, colNew)
  def fieldsize: Int = field.size
  def getRoundManager: RoundManager = mgr
}