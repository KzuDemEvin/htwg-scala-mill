import de.htwg.se.mill.model.{Cell, Color, Field, Stone}


val stone1 = Stone(0, Color.white)
val stone2 = Stone(1, Color.white)
val stone3 = Stone(0, Color.black)


//case class Field(stones: Vector[Stone])

val playground = Vector[Stone]()
playground ++ Seq(stone1, stone2, stone3)

val colorStone1 = stone1.whichColor
val isSet = stone1.value
val stoneB = Stone(0, Color.white)
printf("Color stone1: %s", colorStone1)

def printGameboard(): String = {
  val gameboard =
    """|
       |1------2------3
       || 4----5----6 |
       || | 7--8--9 | |
       |0-0-0     0-0-0
       || | 0--0--0 | |
       || 0----0----0 |
       |0------0------0
       |""".stripMargin
  return gameboard
}
val gb = printGameboard()

printf("%s", gb)

//def coordinates(x1:Int, x2:Int, x3:Int):T = allCells (x1)(x2)
//def replaceStone(square: Int, col: Int, row: Int): Field[T] = {
//  copy()
//}

case class Field[T](field: Vector[T]) {

  val size:Int = field.size
}

val field1 = Field(Vector(Cell(true),Cell(false),Cell(true)))

case class FieldEasy[T](field:Vector[T]){
  def this(size:Int, filling:T) = this(Vector.tabulate(size){(size) => filling})
  val size:Int = field.size
  def cell(position:Int):T = field (position)
  def refill(filling:T):FieldEasy[T]= copy(Vector.tabulate(size){size => filling})
  def replaceCell(position:Int, cell: T):FieldEasy[T] = copy(field.updated(position, cell))
}

val field2 = FieldEasy(Vector(Vector(Cell(true))))
field2.size

val field3 = new FieldEasy[Cell](3, Cell(true))
field3.cell(0)
field3.refill(Cell(true))
field3.replaceCell(2, Cell(false))