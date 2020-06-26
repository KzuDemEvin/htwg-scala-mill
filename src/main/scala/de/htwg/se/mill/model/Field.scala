package de.htwg.se.mill.model


case class Field(allCells: Matrix[Cell]) {
  def this(size: Int) {
    this(new Matrix[Cell](size, Cell(false, Stone("n"))))
  }

  val size: Int = allCells.size

  def cell(row: Int, col: Int): Cell = allCells.cell(row, col)

  def possiblePosition(row: Int, col: Int): Boolean = allCells.allowedCell(row, col)

  def available(row: Int, col: Int): Boolean = if (possiblePosition(row, col) && !cell(row, col).isSet) true else false

  def set(row:Int, col:Int, c:Cell) : Field = {
    if (available(row, col)) {
      replace(row, col, c)
    } else {this}
  }

  def replace(row:Int, col:Int, c:Cell) : Field = {
    copy(allCells.replaceCell(row, col, c))
  }

  def placedStones(): Int = {
    var placedStones = 0
    for (x <- this.allCells.allowedPosition) {
      if (!this.available(x._1, x._2)) {
        placedStones = placedStones + 1
      }
    }
    placedStones
  }

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

  def checkMill():Int = {
    var r = 0
    for (x <- millPositions) {
      val cell1 = cell(x._1._1, x._1._2)
      val cell2 = cell(x._2._1, x._2._2)
      val cell3 = cell(x._3._1, x._3._2)

      if (checkMillSet(cell1, cell2, cell3)) {
        if (checkMillBlack(cell1, cell2, cell3)) {
          r = 1
        }
        if (checkMillWhite(cell1, cell2, cell3)) {
          r = 2
        }
      }
    }
    r
  }

//  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Field = {
//    for (x <- neighbours(rowOld, colOld)) {
//      if (x._1 = rowNew && x._2 == colNew) {
//
//      }
//    }
//
//  }

  val neighbours = Map((0,0) -> Set((0,3),(3,0)),
                       (0,3) -> Set((0,0),(0,6),(3,1)),
                       (0,6) -> Set((0,3),(6,3)),
                       (1,1) -> Set((1,3),(3,1)),
                       (1,3) -> Set((1,1),(1,5),(0,3),(2,3)),
                       (1,5) -> Set((1,3),(3,5)),
                       (2,2) -> Set((3,2),(2,3)),
                       (2,3) -> Set((2,2),(2,4),(1,3)),
                       (2,4) -> Set((2,3),(3,4)),
                       (3,0) -> Set((0,0),(6,0),(3,1)),
                       (3,1) -> Set((3,0),(3,2),(1,1),(5,1)),
                       (3,2) -> Set((2,2),(4,2),(3,1)),
                       (3,4) -> Set((2,4),(4,4),(3,5)),
                       (3,5) -> Set((3,4),(3,6)),
                       (3,6) -> Set((0,6),(6,6),(3,5)),
                       (4,2) -> Set((3,2),(4,3)),
                       (4,3) -> Set((4,2),(4,4),(3,5)),
                       (4,4) -> Set((4,3),(3,4)),
                       (5,1) -> Set((3,1),(5,3)),
                       (5,3) -> Set((5,1),(5,5),(4,3),(6,3)),
                       (5,5) -> Set((5,3),(3,5)),
                       (6,0) -> Set((3,0),(6,3)),
                       (6,3) -> Set((6,0),(6,6),(5,3)),
                       (6,6) -> Set((6,3),(3,6)))


  private def checkMillSet(cell1:Cell, cell2:Cell, cell3:Cell):Boolean = {
    cell1.isSet && cell2.isSet && cell3.isSet
  }

  private def checkMillBlack(cell1:Cell, cell2:Cell, cell3:Cell):Boolean = {
    print("Test")
    (cell1.getContent.whichColor == Color.black && cell2.getContent.whichColor == Color.black
    && cell3.getContent.whichColor == Color.black)
  }

  private def checkMillWhite(cell1:Cell, cell2:Cell, cell3:Cell):Boolean = {
    (cell1.getContent.whichColor == Color.white && cell2.getContent.whichColor == Color.white
      && cell3.getContent.whichColor == Color.white)
  }


  override def toString: String = {
    var string = "Mill Gameboard:\n"
    var counter = 0
    for (a <- 0 until size) {
      for (b <- 0 until size) {
        if (counter == 7) {
          string += "\n"
          counter = 0
        }
        if (possiblePosition(a, b)) {
          counter = counter + 1
          if (this.cell(a, b).content.whichColor == Color.white) {
            string += " w "
          } else if (this.cell(a, b). content.whichColor == Color.black) {
            string += " b "
          } else {
            string += " o "
          }
        } else {
          counter = counter + 1
          string += " - "
        }
      }
    }
    string += "\n"
    string
  }
}