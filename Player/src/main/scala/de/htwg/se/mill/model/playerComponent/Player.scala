package de.htwg.se.mill.model.playerComponent

trait Player {
  val name: String
  val amountStones: Int
  val mode: String

  def changeMode(mode: String): Player
}

private case class ClassicPlayer(override val name: String, override val mode: String, override val amountStones: Int) extends Player {
  def this(name: String) =
    this(
      name = name,
      mode = "SetMode",
      amountStones = 9
    )

  def this(name: String, amountStones: Int) = this(
    name = name,
    mode = "SetMode",
    amountStones = amountStones
  )

  override def changeMode(mode: String): Player = copy(mode = mode)

  override def toString: String = "Name: " + name + ", Amount of Stones: " + amountStones
}

object Player {
  def apply(name: String): Player = new ClassicPlayer(name)

  def apply(name: String, amountStones: Int): Player = new ClassicPlayer(name, amountStones)
}

