package de.htwg.se.mill.model


case class Field(allCells: Matrix[Cell]) {
  def this(size:Int) {
    this(new Matrix[Cell](size, Cell(false, Stone(0, Color.noColor))))
  }

  val size:Int = allCells.size

  def cell(row:Int, col:Int):Cell = allCells.cell(row, col)

  def possiblePosition(row:Int, col:Int):Boolean = allCells.allowedPosition.contains((row, col))

  def available(row:Int, col:Int):Boolean = {
    //val celltmp = cell(row, col)
//    val cell1 = if(celltmp == Cell(false, Stone(0, Color.noColor))) {
//        Cell(false, Stone(0, Color.noColor))}
//    else if (celltmp == Cell(true, Stone(1, Color.black))) {
//      Cell(true, Stone(1, Color.black))
//    } else if (celltmp == Cell(true, Stone(1, Color.white))) {
//      Cell(true, Stone(1, Color.white))
//    } else {
//      throw new NoSuchElementException
//    }
    if (allCells.allowedCell(row, col) && cell(row, col).isSet) true else false
  }

  def set(row:Int, col:Int, c:Cell) : Field = copy(allCells.replaceCell(row, col, c))
}