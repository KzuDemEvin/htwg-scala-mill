package de.htwg.se.mill.model


case class Field[Cell](field: Vector[Vector[Cell]]) {
  def this(size:Int, filling:Cell) = this(Vector.tabulate(size, size){(x1, x2) => filling})

  val allowedPosition = List((0,0),(0,3),(0,6),(1,1),(1,3),(1,5),(2,2),(2,4),(3,0),(3,1),(3,2),
    (3,4),(3,5),(3,6),(4,2),(4,3),(4,4),(5,1),(6,0),(6,3),(6,6))

  val size:Int = field.size

  def cell(row:Int, col:Int):Cell = field (row)(col)

  def allowedCell(row:Int, col:Int):Boolean = allowedPosition.contains((row, col))

  def refill(filling:Cell):Field[Cell] = copy(Vector.tabulate(size, size){(x1, x2) => filling})

  def replaceCell(x1:Int, x2:Int, cell:Cell):Field[Cell] = copy(field.updated(x1, field(x1).updated(x2, cell)))

  def available(row:Int, col:Int):Boolean = {
    val cellTmp = cell(row,col)
    val celle = if(cellTmp == Cell(true)) Cell(true) else Cell(false)
    if (celle.isSet && allowedCell(row, col)) true else false
  }

  def printGameboard(): String = {
    val sb = "Your Mill field:\n"
    var a, b, c = 0
    var t = (size - 1) / 2
    for( a <- 0 until size){
      for (b <- 0 until size) {
        if ((a == t || a == size - 4) && (b != t)) { // middle of the field
          x = x + 1
          sb + " o "
        } else if((a == 0 || a == size - 1) && (b == 0 + x * t )) {
          x = x + 1
          sb + " o "
        } else if ((a == 1 || a == size - 2) && (b == 1 + x * (t - 1))) {
          x = x + 1
          sb + " o "
        } else if ((a == 2 || a == size - 3) && (b == 2 + x * (t - 2))
          && (b < size - 2)) {
          x = x + 1
          sb + " o "
        } else {
          sb + " o "
        }
      }
      x = 0
      sb + "\n"
    }
    sb
  }

  override def toString: String = {
    val string = ""
    string
  }
}