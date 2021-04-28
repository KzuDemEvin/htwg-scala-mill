package de.htwg.se.mill.controller.controllerRoundManager

import de.htwg.se.mill.model.fieldComponent.Cell

trait RoundManagerControllerInterface {

  def handleClick(row: Int, col: Int): String
  def undo(): String
  def setField(field: String): String
  def createEmptyField(size: Int): String
  def createRandomField(size: Int): String
  def turn(): String
  def roundCounter(): String
  def winner(): String
  def winnerText(): String
  def cell(row: Int, col: Int): Cell
  def isSet(row: Int, col: Int): String
  def color(row: Int, col: Int): String
  def possiblePosition(row: Int, col: Int): String
  def millState(): String
  def fieldAsJson(): String
  def fieldAsHtml(): String
  def fieldAsString(): String
}
