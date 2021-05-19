package de.htwg.se.mill.model.fieldComponent

trait FieldInterface {
  def size: Int

  def cell(row: Int, col: Int): Cell

  def cellsWithIndex: Vector[((Int, Int), Cell)]

  def possiblePosition(row: Int, col: Int): Boolean

  def available(row: Int, col: Int): Boolean

  def set(row: Int, col: Int, c: Cell): (FieldInterface, Boolean)

  def replace(row: Int, col: Int, c: Cell): FieldInterface

  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): (FieldInterface, Boolean)

  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): (FieldInterface, Boolean)

  def removeStone(row: Int, col: Int, ignore: Boolean = false): (FieldInterface, Boolean)

  def isNeighbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Boolean

  def checkIfCanMove(row: Int, col: Int): Boolean

  def placedStones(): Int

  def placedWhiteStones(): Int

  def placedBlackStones(): Int

  def resetMill(): FieldInterface

  def checkMill(row: Int, col: Int): FieldInterface

  def toHtml: String

  def createNewField: FieldInterface

  def setRoundCounter(counter: Int): FieldInterface

  val savedRoundCounter: Int

  def setPlayer1Mode(mode: String): FieldInterface

  val player1Mode: String

  def setPlayer1Name(name: String): FieldInterface

  val player1Name: String

  def setPlayer2Mode(mode: String): FieldInterface

  val player2Mode: String

  def setPlayer2Name(name: String): FieldInterface

  val player2Name: String

  val millState: String
}
