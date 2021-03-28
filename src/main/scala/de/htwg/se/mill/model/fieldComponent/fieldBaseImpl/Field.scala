package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import com.google.inject.Inject
import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}

case class Field @Inject()(allCells: Matrix[Cell],
                           savedRoundCounter: Int,
                           player1Mode: String,
                           player1Name: String,
                           player2Mode: String,
                           player2Name: String) extends FieldInterface {

  def this(allCells: Matrix[Cell]) {

    this(allCells = allCells,
      savedRoundCounter = 0,
      player1Mode = ModeState.handle(SetModeState()),
      player1Name = "",
      player2Mode = ModeState.handle(SetModeState()),
      player2Name = "")
  }

  def this(size: Int) {
    this(new Matrix[Cell](size, Cell("ce")))
  }

  val size: Int = allCells.size

  def cell(row: Int, col: Int): Cell = allCells.cell(row, col)

  def possiblePosition(row: Int, col: Int): Boolean = allCells.allowedCell(row, col)

  def available(row: Int, col: Int): Boolean = possiblePosition(row, col) && !cell(row, col).isSet

  def set(row: Int, col: Int, c: Cell): Field = {
    if (available(row, col)) {
      replace(row, col, c)
    } else {
      copy()
    }
  }

  def replace(row: Int, col: Int, c: Cell): Field = {
    copy(allCells.replaceCell(row, col, c))
  }

  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Field = {
    if (neighbours(rowOld, colOld).contains((rowNew, colNew)) && !cell(rowNew, colNew).isSet) {
      fly(rowOld, colOld, rowNew, colNew)
    } else {
      copy()
    }
  }

  def isNeighbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Boolean = {
    neighbours(rowOld, colOld).contains((rowNew, colNew))
  }

  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Field = {
    copy().replace(rowOld, colOld, Cell("ce")).set(rowNew, colNew, cell(rowOld, colOld))
  }

  def removeStone(row: Int, col: Int): (Field, Boolean) = {
    var field = copy()
    if (checkMill(row, col) == 0) {
      field = field.replace(row, col, Cell("ce"))
      (field, true)
    } else {
      (field, false)
    }
  }

  // (Whitestones, Blackstones)
  private def placedStonesCounter(): (Int, Int) = {
    var whiteStones = 0
    var blackStones = 0
    for (x <- this.allCells.allowedPosition) {
      if (!this.available(x._1, x._2)) {
        if (this.cell(x._1, x._2).content.color.equals(Color.white)) {
          whiteStones += 1
        } else if (this.cell(x._1, x._2).content.color.equals(Color.black)) {
          blackStones += 1
        }
      }
    }
    (whiteStones, blackStones)
  }

  def placedStones(): Int = placedStonesCounter()._1 + placedStonesCounter()._2

  def placedWhiteStones(): Int = placedStonesCounter()._1

  def placedBlackStones(): Int = placedStonesCounter()._2

  val millPositions = List(((0, 0), (0, 3), (0, 6)), //horizontal mills
    ((1, 1), (1, 3), (1, 5)),
    ((2, 2), (2, 3), (2, 4)),
    ((3, 0), (3, 1), (3, 2)),
    ((3, 4), (3, 5), (3, 6)),
    ((4, 2), (4, 3), (4, 4)),
    ((5, 1), (5, 3), (5, 5)),
    ((6, 0), (6, 3), (6, 6)),
    ((0, 0), (3, 0), (6, 0)), //vertical mills
    ((1, 1), (3, 1), (5, 1)),
    ((2, 2), (3, 2), (4, 2)),
    ((0, 3), (1, 3), (2, 3)),
    ((4, 3), (5, 3), (6, 3)),
    ((2, 4), (3, 4), (4, 4)),
    ((1, 5), (3, 5), (5, 5)),
    ((0, 6), (3, 6), (6, 6)))

  val millneighbours = Map((0, 0) -> Set(((0, 3), (0, 6)), ((3, 0), (6, 0))),
    (0, 3) -> Set(((0, 0), (0, 6)), ((1, 3), (2, 3))),
    (0, 6) -> Set(((0, 0), (0, 3)), ((3, 6), (6, 6))),
    (1, 1) -> Set(((1, 3), (1, 5)), ((3, 1), (5, 1))),
    (1, 3) -> Set(((1, 1), (1, 5)), ((0, 3), (2, 3))),
    (1, 5) -> Set(((1, 1), (1, 3)), ((3, 5), (5, 5))),
    (2, 2) -> Set(((2, 3), (2, 4)), ((3, 2), (4, 2))),
    (2, 3) -> Set(((2, 2), (2, 4)), ((0, 3), (1, 3))),
    (2, 4) -> Set(((2, 2), (2, 3)), ((3, 4), (4, 4))),
    (3, 0) -> Set(((3, 1), (3, 2)), ((0, 0), (6, 0))),
    (3, 1) -> Set(((3, 0), (3, 2)), ((1, 1), (5, 1))),
    (3, 2) -> Set(((3, 0), (3, 1)), ((2, 2), (4, 2))),
    (3, 4) -> Set(((3, 5), (3, 6)), ((2, 4), (4, 4))),
    (3, 5) -> Set(((3, 4), (3, 6)), ((1, 5), (5, 5))),
    (3, 6) -> Set(((3, 4), (3, 5)), ((0, 6), (6, 6))),
    (4, 2) -> Set(((4, 3), (4, 4)), ((2, 2), (3, 2))),
    (4, 3) -> Set(((4, 2), (4, 4)), ((5, 3), (6, 3))),
    (4, 4) -> Set(((4, 2), (4, 3)), ((2, 4), (3, 4))),
    (5, 1) -> Set(((5, 3), (5, 5)), ((1, 1), (3, 1))),
    (5, 3) -> Set(((5, 1), (5, 5)), ((4, 3), (6, 3))),
    (5, 5) -> Set(((5, 1), (5, 3)), ((1, 5), (3, 5))),
    (6, 0) -> Set(((6, 3), (6, 6)), ((0, 0), (3, 0))),
    (6, 3) -> Set(((6, 0), (6, 6)), ((4, 3), (5, 3))),
    (6, 6) -> Set(((6, 0), (6, 3)), ((0, 6), (3, 6))))

  val neighbours = Map((0, 0) -> Set((0, 3), (3, 0)),
    (0, 3) -> Set((0, 0), (0, 6), (1, 3)),
    (0, 6) -> Set((0, 3), (3, 6)),
    (1, 1) -> Set((1, 3), (3, 1)),
    (1, 3) -> Set((1, 1), (1, 5), (0, 3), (2, 3)),
    (1, 5) -> Set((1, 3), (3, 5)),
    (2, 2) -> Set((3, 2), (2, 3)),
    (2, 3) -> Set((2, 2), (2, 4), (1, 3)),
    (2, 4) -> Set((2, 3), (3, 4)),
    (3, 0) -> Set((0, 0), (6, 0), (3, 1)),
    (3, 1) -> Set((3, 0), (3, 2), (1, 1), (5, 1)),
    (3, 2) -> Set((2, 2), (4, 2), (3, 1)),
    (3, 4) -> Set((2, 4), (4, 4), (3, 5)),
    (3, 5) -> Set((3, 4), (3, 6), (1, 5), (5, 5)),
    (3, 6) -> Set((0, 6), (6, 6), (3, 5)),
    (4, 2) -> Set((3, 2), (4, 3)),
    (4, 3) -> Set((4, 2), (4, 4), (5, 3)),
    (4, 4) -> Set((4, 3), (3, 4)),
    (5, 1) -> Set((3, 1), (5, 3)),
    (5, 3) -> Set((5, 1), (5, 5), (4, 3), (6, 3)),
    (5, 5) -> Set((5, 3), (3, 5)),
    (6, 0) -> Set((3, 0), (6, 3)),
    (6, 3) -> Set((6, 0), (6, 6), (5, 3)),
    (6, 6) -> Set((6, 3), (3, 6)))


  def checkMill(row: Int, col: Int): Int = {
    var millYesNo = 0
    val cell1 = cell(row, col)
    for ((x1, x2) <- millneighbours(row, col)) {
      val cell2 = cell(x1._1, x1._2)
      val cell3 = cell(x2._1, x2._2)
      if (checkMillSet(cell1, cell2, cell3)) {
        if (checkMillBlack(cell1, cell2, cell3)) {
          millYesNo = 1
        } else if (checkMillWhite(cell1, cell2, cell3)) {
          millYesNo = 2
        }
      }
    }
    millYesNo
  }

  private def checker[T](check: T => Boolean)(values: Vector[T]): Boolean = values.forall(check(_))

  private def checkMillSet(cell1: Cell, cell2: Cell, cell3: Cell): Boolean = checker[Cell](value => value.isSet)(Vector(cell1, cell2, cell3))

  private def checkMillColor(color: Color.Value)(cell1: Cell, cell2: Cell, cell3: Cell): Boolean = {
    checker[Cell](value => value.content.color == color)(Vector(cell1, cell2, cell3))
  }

  private def checkMillBlack(cell1: Cell, cell2: Cell, cell3: Cell): Boolean = checkMillColor(Color.black)(cell1, cell2, cell3)

  private def checkMillWhite(cell1: Cell, cell2: Cell, cell3: Cell): Boolean = checkMillColor(Color.white)(cell1, cell2, cell3)

  override def createNewField: FieldInterface = new Field(size)

  override def toString: String = {
    var string = "Mill Gameboard:\n"
    for (a <- 0 until size) {
      for (b <- 0 until size) {
        if (possiblePosition(a, b)) {
          string += s" ${this.cell(a, b).colorAsChar} "
        } else {
          string += " - "
        }
      }
      string += "\n"
    }
    string
  }

  def setRoundCounter(counter: Int): Field = copy(savedRoundCounter = counter)

  def setPlayer1Mode(mode: String): Field = copy(player1Mode = ModeState.handle(checkModeState(mode)))

  def setPlayer1Name(name: String): Field = copy(player1Name = name)

  def setPlayer2Mode(mode: String): Field = copy(player2Mode = ModeState.handle(checkModeState(mode)))

  def setPlayer2Name(name: String): Field = copy(player2Name = name)

  def checkModeState(mode: String): ModeState = {
    mode match {
      case "SetMode" => SetModeState()
      case "MoveMode" => MoveModeState()
      case "FlyMode" => FlyModeState()
    }
  }
}