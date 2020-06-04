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

//  def checkMiddle(a:Int):Boolean = {
//
//  }
//  def printGameboard(): Unit = {
//    var a, b, x = 0
//    var t = (size - 1) / 2
//    for( a <- 0 until size){
//      for (b <- 0 until size) {
//        if (((a == t || a == size - 4) && (b != t))
//          || ((a == 0 || a == size - 1) && (b == 0 + x * t ))
//          || ((a == 1 || a == size - 2) && (b == 1 + x * (t - 1)))
//          || ((a == 2 || a == size - 3) && (b == 2 + x * (t - 2)) && (b < size - 2))) {
//          printf(" o ")
//          x = x + 1
//        } else {
//          printf(" - ")
//        }
//      }
//      x = 0
//      printf("\n")
//    }
//  }
//
//  override def toString: String = {
//
//  }
}