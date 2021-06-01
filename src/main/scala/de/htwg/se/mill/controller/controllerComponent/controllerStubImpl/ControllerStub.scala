package de.htwg.se.mill.controller.controllerComponent.controllerStubImpl

import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

class ControllerStub extends ControllerInterface {
  override var gameState: String = _
  // override var cachedField: Option[JsValue] = _

  var roundCounter: Int = 0
  var color: Int = 1
  var isSet: Boolean = false
  var possiblePosition: Boolean = false
  var winner: Int = 0
  var winnerText: String = "No Winner"
  var millState: String = "No Mill"

  override def createPlayer(name: String, number: Int): String = name
  override def createEmptyField(size: Int): Unit = print(s"Created empty field of size $size.")
  override def createRandomField(size: Int): Unit = print(s"Created random field of size $size.")
  override def fieldToString(oncomplete: Option[String] => Unit): Unit = oncomplete(Some("FieldAsString"))
  override def fieldToHtml(oncomplete: Option[String] => Unit): Unit = oncomplete(Some("FieldAsHtml"))
  override def fieldToHtmlSync: String = "FieldAsHtml"
  override def fieldToJson(oncomplete: Option[String] => Unit): Unit = oncomplete(Some("FieldAsJson"))
  override def getRoundCounter(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(roundCounter.toString))
  override def handleClick(row: Int, column: Int)(oncomplete: Option[String] => Unit): Unit = oncomplete(Some((row, column).toString()))
  override def undo(): Unit = {}
  override def redo(): Unit = {}
  override def changeSaveMethod(method: String): Unit = {}
  override def save(): Unit = {}
  override def load(): Unit = {}
  override def saveDB(): Unit = {}
  override def loadDB(id: Int): Unit = {}
  override def color(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(color.toString))
  override def isSet(row: Int, col: Int)(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(isSet.toString))
  override def possiblePosition(row: Int, col: Int): Boolean = possiblePosition
  override def fieldsize: Int = 7
  override def getWinner(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(winner.toString))
  override def getWinnerText(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(winnerText))
  override def getMillState(oncomplete: Option[String] => Unit): Unit = oncomplete(Some(millState))

}
