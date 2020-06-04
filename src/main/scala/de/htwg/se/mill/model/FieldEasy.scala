package de.htwg.se.mill.model

case class FieldEasy[T](field:Vector[T]){
  def this(size:Int, filling:T) = this(Vector.tabulate(size){ _ => filling})
  val size:Int = field.size
  def cell(position:Int):T = field(position)
  def refill(filling:T):FieldEasy[T]= copy(Vector.tabulate(size){ _ => filling})
  def replaceCell(position:Int, cell: T):FieldEasy[T] = copy(field.updated(position, cell))
  //def set(pos:Int, status:Boolean):FieldEasy[T] = copy(field.replaceCell(pos, cell(0)))
}
