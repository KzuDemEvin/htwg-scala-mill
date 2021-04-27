import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.fieldComponent.{Color, Stone, fieldBaseImpl}


//val stone = Stones1("w")

val colorset = Color.values.toIndexedSeq
val h = colorset.apply(0)
val s = Color.black
println(s)

var field = new Field(7)
field = field.set(0,0, Cell(true, Stone("b+")))
field = field.set(0,3, fieldBaseImpl.Cell(true, Stone("b+")))
field = field.set(0,6, fieldBaseImpl.Cell(true, Stone("b+")))

field.toString

//var string = ""
//var a, b, x = 0
//var t = (gb.size - 1) / 2
//for( a <- 0 until gb.size){
//  for (b <- 0 until gb.size) {
//    if (((a == t || a == gb.size - 4) && (b != t))
//    || ((a == 0 || a == gb.size - 1) && (b == 0 + x * t ))
//    || ((a == 1 || a == gb.size - 2) && (b == 1 + x * (t - 1)))
//    || ((a == 2 || a == gb.size - 3) && (b == 2 + x * (t - 2)) && (b < gb.size - 2))) {
//      string + (" o ")
//      //string.concat(" o ")
//      x = x + 1
//    } else {
//      string + (" - ")
//    }
//  }
//  x = 0
//  printf("\n")
//}

field = field.moveStone(0,0,3,0)
field.toString


















val mill = field.checkMill()
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
val mill = field.checkMill()