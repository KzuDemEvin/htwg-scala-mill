package de.htwg.se.mill.controller.controllerRoundManager

import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.RoundManagerModule
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.roundManagerComponent.RoundManager
import de.htwg.se.mill.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json.{JsNumber, JsValue, JsString, Json}

class RoundManagerController @Inject()(var field: FieldInterface) extends RoundManagerControllerInterface {
  var mgr: RoundManager = RoundManager(field)
  val injector: Injector = Guice.createInjector(new RoundManagerModule)
  private val undoManager: UndoManager = new UndoManager
  doStep()

  def handleClick(row: Int, col: Int): String = {
    print(s"HandleClick called!\n")
    mgr = mgr.handleClick(row, col)
    doStep()
    print(mgr.update)
    new Gson().toJson(mgr.update)
  }

  def doStep(): String = {
    undoManager.doStep(mgr.copy())
    fieldAsJson()
  }

  def undo() = {
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
    mgr = mgr.copy(field = field, roundCounter = field.savedRoundCounter, player1Mode = field.player1Mode, player2Mode = field.player2Mode)
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
  def winnerText(): String = {
    print(s"WinnerText called!\n")
    new Gson().toJson(mgr.winnerText)
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
    })
  }
  def possiblePosition(row: Int, col: Int): String = new Gson().toJson(mgr.field.possiblePosition(row, col))
  def millState(): String = new Gson().toJson(mgr.field.millState)

  def fieldAsJson(): String = {
    val fieldAsJson = Json.obj(
      "field" -> Json.obj(
        "roundCounter" -> JsNumber(mgr.roundCounter),
        "player1Mode" -> JsString(mgr.player1Mode),
        "player2Mode" -> JsString(mgr.player2Mode),
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
    )
    Json.prettyPrint(fieldAsJson)
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
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      val color = (json \\ "color")(index).toString().replaceAll("\"", "")
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
}