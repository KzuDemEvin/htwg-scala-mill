package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import com.google.inject.Inject
import de.htwg.se.mill.model.fieldComponent._
import de.htwg.se.mill.model.{ModeState, SetModeState}

import scala.util.{Failure, Success, Try}

case class Field @Inject()(allCells: Matrix[Cell],
                           savedRoundCounter: Int,
                           player1Mode: String,
                           player1Name: String,
                           player2Mode: String,
                           player2Name: String,
                           millState: String) extends FieldInterface {

  def this(allCells: Matrix[Cell]) {
    this(allCells = allCells,
      savedRoundCounter = 0,
      player1Mode = ModeState.handle(SetModeState()),
      player1Name = "",
      player2Mode = ModeState.handle(SetModeState()),
      player2Name = "",
      millState = MillState.handle(NoMillState())
    )
  }

  def this(size: Int) {
    this(new Matrix[Cell](size, Cell("ce")))
  }

  val size: Int = allCells.size

  def cell(row: Int, col: Int): Cell = allCells.cell(row, col)

  def cellsWithIndex: Vector[((Int, Int), Cell)] = allCells.cellsWithIndex()

  def possiblePosition(row: Int, col: Int): Boolean = allCells.allowedCell(row, col)

  def available(row: Int, col: Int): Boolean = possiblePosition(row, col) && !cell(row, col).isSet

  def set(row: Int, col: Int, c: Cell): (Field, Boolean) = {
    if (available(row, col)) {
      (replace(row, col, c), true)
    } else {
      (copy(), false)
    }
  }

  def replace(row: Int, col: Int, c: Cell): Field = copy(allCells.replaceCell(row, col, c))

  def checkIfCanMove(row: Int, col: Int): Boolean = neighbours(row, col).exists(c => !cell(c._1, c._2).isSet)

  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): (Field, Boolean) = {
    if (isNeighbour(rowOld, colOld, rowNew, colNew) && !cell(rowNew, colNew).isSet) {
      fly(rowOld, colOld, rowNew, colNew)
    } else {
      (copy(), false)
    }
  }

  def isNeighbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Boolean = {
    neighbours(rowOld, colOld).contains((rowNew, colNew))
  }

  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): (Field, Boolean) = {
    copy().replace(rowOld, colOld, Cell("ce")).set(rowNew, colNew, cell(rowOld, colOld))
  }

  def removeStone(row: Int, col: Int, ignore: Boolean = false): (Field, Boolean) = {
    val field = checkMill(row, col)
    if (field.millState == NoMillState().handle || ignore) {
      (field.replace(row, col, Cell("ce")), true)
    } else {
      (field, false)
    }
  }

  private def placedStonesCounter(color: Color.Value): Int = {
    this.allCells.allowedPosition.count(x => !this.available(x._1, x._2) && this.cell(x._1, x._2).content.color.equals(color))
  }

  def placedStones(): Int = placedWhiteStones() + placedBlackStones()

  def placedWhiteStones(): Int = placedStonesCounter(Color.white)

  def placedBlackStones(): Int = placedStonesCounter(Color.black)

  val millneighbours = Map(
    (0, 0) -> Set(((0, 3), (0, 6)), ((3, 0), (6, 0))),
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

  def resetMill(): Field = copy(millState = MillState.handle(NoMillState()))

  def checkMill(row: Int, col: Int): Field = {
    val checkMill: ((Cell, Cell, Cell) => Boolean) => Boolean = checkMillC(row, col)((c1, c2, c3) => checkMillSet(c1, c2, c3))

    copy(millState = if (checkMill((c1, c2, c3) => checkMillBlack(c1, c2, c3))) {
      MillState.handle(BlackMillState())
    } else if (checkMill((c1, c2, c3) => checkMillWhite(c1, c2, c3))) {
      MillState.handle(WhiteMillState())
    } else {
      MillState.handle(NoMillState())
    })
  }

  private def checkMillC(row: Int, col: Int)(checkAll: (Cell, Cell, Cell) => Boolean)(check: (Cell, Cell, Cell) => Boolean): Boolean = {
    val cell1 = cell(row, col)
    Try(millneighbours(row, col)) match {
      case Success(n) => (for {
        (x1, x2) <- n
        cell2 = cell(x1._1, x1._2)
        cell3 = cell(x2._1, x2._2)
        if checkAll(cell1, cell2, cell3)
      } yield {
        (cell1, cell2, cell3)
      }).exists(c => check(c._1, c._2, c._3))
      case Failure(_) => false
    }
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
    s"Mill Gameboard:\n${
      (for {
        a <- 0 until size
      } yield {
        (for {
          b <- 0 until size
        } yield {
          if (possiblePosition(a, b)) {
            s" ${this.cell(a, b).colorAsChar} "
          } else {
            " - "
          }
        }).mkString("")
      }).mkString("\n")
    }\n"
  }

  override def toHtml: String = "<p  style=\"font-family:'Lucida Console',monospace\"> " + toString.replace("\n", "<br>") + "</p>"

  def setRoundCounter(counter: Int): Field = copy(savedRoundCounter = counter)

  def setPlayer1Mode(mode: String): Field = copy(player1Mode = ModeState.handle(checkModeState(mode)))

  def setPlayer1Name(name: String): Field = copy(player1Name = name)

  def setPlayer2Mode(mode: String): Field = copy(player2Mode = ModeState.handle(checkModeState(mode)))

  def setPlayer2Name(name: String): Field = copy(player2Name = name)

  def checkModeState(mode: String): ModeState = ModeState.whichState(mode)
}