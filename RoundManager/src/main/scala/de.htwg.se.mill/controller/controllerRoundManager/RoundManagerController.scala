package de.htwg.se.mill.controller.controllerRoundManager

import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.RoundManagerModule
import de.htwg.se.mill.model.fieldComponent.{Cell, FieldInterface}
import de.htwg.se.mill.model.roundManagerComponent.RoundManager
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json.{JsNumber, JsString, Json}

class RoundManagerController @Inject()(var field: FieldInterface) extends RoundManagerControllerInterface {
  var mgr: RoundManager = RoundManager(field)
  val injector: Injector = Guice.createInjector(new RoundManagerModule)

  def handleClick(row: Int, col: Int): String = {
    mgr = mgr.handleClick(row, col)
    fieldAsJson()
  }

  def createEmptyField(size: Int): String = {
    mgr = mgr.copy(winner = 0, roundCounter = 0, field = injector.instance[FieldInterface](Names.named("normal"))).modeChoice()
    fieldAsJson()
  }

  def createRandomField(size: Int): String = {
    mgr = mgr.copy(winner = 0, roundCounter = mgr.borderToMoveMode, field = injector.instance[FieldInterface](Names.named("random"))).modeChoice()
    fieldAsJson()
  }

  def turn(): String = new Gson().toJson(if (mgr.blackTurn()) "Black" else "White")
  def roundCounter(): String = new Gson().toJson(mgr.roundCounter)
  def winner(): String = new Gson().toJson(mgr.winner)
  def winnerText(): String = new Gson().toJson(mgr.winnerText)
  def cell(row: Int, col: Int): Cell = mgr.field.cell(row, col)
  def isSet(row: Int, col: Int): String = new Gson().toJson(cell(row, col).isSet)
  def color(row: Int, col: Int): String = new Gson().toJson(cell(row, col).content.color.toString)
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

  def fieldAsHtml(): String = mgr.field.toHtml

  def fieldAsString(): String = mgr.field.toString
}