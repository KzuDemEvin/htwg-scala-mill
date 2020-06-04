import de.htwg.se.mill.model.{Cell, Color, Field, FieldCreator, Stone}

import scala.util.Random



FieldCreator(3)

val b = Cell(filled = false, Stone(1, Color.white))
val colorset = Color.values.toIndexedSeq
val h = colorset.apply(0)




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
//
//
//
//
//Set[Int](2,3).toIndexedSeq