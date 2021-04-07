package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.controller.controllerComponent.{ModeState, SetModeState}

trait Player {
  val name: String
  val amountStones: Int
  val mode: String

  def changeMode(mode: String): Player
}

private case class ClassicPlayer(override val name: String, override val mode: String, override val amountStones: Int) extends Player {
  def this(name: String) = this(name = name, mode = ModeState.handle(SetModeState()), amountStones = 9)

  def this(name: String, amountStones: Int) = this(name = name, mode = ModeState.handle(SetModeState()), amountStones = amountStones)


  override def changeMode(mode: String): Player = copy(mode = mode)

  override def toString: String = "Name: " + name + ", Amount of Stones: " + amountStones
}

object Player {
  def apply(name: String): Player = new ClassicPlayer(name)

  def apply(name: String, amountStones: Int): Player = new ClassicPlayer(name, amountStones)
}

