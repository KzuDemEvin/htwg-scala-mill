package de.htwg.se.mill.model


case class Field(allCells: Matrix[Cell]) {
  def this(size:Int) {
    this(new Matrix[Cell](size, Cell(false, Stone(0, Color.noColor))))
  }

  val size:Int = allCells.size

  var placedStones = 0

  def cell(row:Int, col:Int):Cell = allCells.cell(row, col)

  def possiblePosition(row:Int, col:Int):Boolean = allCells.allowedPosition.contains((row, col))

  def available(row:Int, col:Int):Boolean = if (allCells.allowedCell(row, col) && !cell(row, col).isSet) true else false

  def set(row:Int, col:Int, c:Cell) : Field = {
    placedStones += 1
    copy(allCells.replaceCell(row, col, c))
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