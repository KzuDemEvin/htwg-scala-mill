package de.htwg.se.mill.model.playerComponent

trait Player {
  val name: String
  val amountStones: Int
  val mode: String

  def changeMode(mode: String): Player
}

private case class ClassicPlayer(override val name: String = "DeineMudda", override val mode: String = "SetMode", override val amountStones: Int = 9) extends Player {
  def this(name: String) = this(
    name = name
  )

  def this(name: String, amountStones: Int) = this(
    name = name,
    amountStones = amountStones
  )

  override def changeMode(mode: String): Player = copy(mode = mode)

  override def toString: String = "Name: " + name + ", Amount of Stones: " + amountStones
}

object Player {
  def apply(name: String): Player = new ClassicPlayer(name)

  def apply(name: String, amountStones: Int): Player = new ClassicPlayer(name, amountStones)
}

