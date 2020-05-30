import de.htwg.se.mill.model.{Cell, Player, Stone, Color, Field}

//def printGameboard(): String = {
//  val gameboard =
//    """|
//       |1------2------3
//       || 4----5----6 |
//       || | 7--8--9 | |
//       |0-0-0     0-0-0
//       || | 0--0--0 | |
//       || 0----0----0 |
//       |0------0------0
//       |""".stripMargin
//  gameboard
//}
//val gb = printGameboard()
//
//printf("%s", gb)


//2dim
//case class Field[Cell](field: Vector[Vector[Cell]]) {
//  def this(size:Int, filling:Cell) = this(Vector.tabulate(size, size){(x1, x2) => filling})
//  val allowedPosition = List((0,0),(0,3),(0,6),(1,1),(1,3),(1,5),(2,2),(2,4),(3,0),(3,1),(3,2),
//    (3,4),(3,5),(3,6),(4,2),(4,3),(4,4),(5,1),(6,0),(6,3),(6,6))
//  val size:Int = field.size
//  def cell(row:Int, col:Int):Cell = field(row)(col)
//  def allowedCell(row:Int, col:Int):Boolean = allowedPosition.contains((row, col))
//  def refill(filling:Cell):Field[Cell] = copy(Vector.tabulate(size, size){(x1, x2) => filling})
//  def replaceCell(x1:Int, x2:Int, cell:Cell):Field[Cell] = copy(field.updated(x1, field(x1).updated(x2, cell)))
//  def available(row:Int, col:Int):Boolean = {
//    val cellTmp = cell(row,col)
//    val celle = if(cellTmp == Cell(true)) Cell(true) else Cell(false)
//    if (celle.isSet && allowedCell(row, col)) true else false
//  }
//}

//val field = new Field(7, Cell(false))
//field.size

//val pg =  new FieldCreator()
val pg1 = new Player("Kevin")
val test1 = new Field(3, Cell(true))
val c1 = Cell(true)
val c1 = Cell(true)



////1 dimensional
//case class FieldEasy[T](field:Vector[T]){
//  def this(size:Int, filling:T) = this(Vector.tabulate(size){ _ => filling})
//  val size:Int = field.size
//  def cell(position:Int):T = field (position)
//  def refill(filling:T):FieldEasy[T]= copy(Vector.tabulate(size){ _ => filling})
//  def replaceCell(position:Int, cell: T):FieldEasy[T] = copy(field.updated(position, cell))
//}
//
//val field2 = FieldEasy(Vector(Vector(Cell(true))))
//field2.size
//
//val field3 = new FieldEasy[Cell](3, Cell(true))
//field3.cell(0)
//field3.refill(Cell(true))
//field3.replaceCell(2, Cell(false))



//3 dimensional
//case class Matrix[T](allcells:Vector[Vector[Vector[T]]]) {
//  def this(size:Int, filling:T) = this(Vector.tabulate(size, size, size) { (x1, x2, x3) => filling })
//
//  val size: Int = allcells.size
//
//  def cell(x1:Int, x2:Int, x3:Int):T = allcells(x1)(x2)(x3)
//
//  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size, size) {(x1, x2, x3) => filling})
//
//  //def replaceCell(x1:Int, x2:Int, x3:T):Matrix[T] = copy(allcells.updated(x1, allcells(x1).updated(x2, allcells(x2).updated(x3, cell))))
//}
//val v = Vector.tabulate(3, 3, 3) { (x1, x2, x3) => (x1,x2,x3) }
//val v2 = Vector.tabulate(2,2) { (x1,x2) => (x1,x2)}
//val v3 = Vector(Vector.tabulate(2,2) { (x1, x2) => (x1,x2)})
//
//
//
//
//Set[Int](2,3).toIndexedSeq