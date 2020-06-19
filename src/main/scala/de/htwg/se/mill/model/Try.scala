package de.htwg.se.mill.model

trait Try[String] {
  def map(f:String => String):Try[String]
}

case class Success[String](s:String) extends Try[String] {
  def map(f:String => String):Success[String] = Success(f(s))
}

case class Failure[String](s:String) extends Try[String] {
  def map(f:String => String):Failure[String] = Failure(f(s))
}