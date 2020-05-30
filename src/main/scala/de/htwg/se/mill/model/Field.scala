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
}