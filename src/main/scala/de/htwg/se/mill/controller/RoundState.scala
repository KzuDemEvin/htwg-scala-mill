package de.htwg.se.mill.controller

trait RoundState {
  def handle:String
}

case class SetModeState() extends GameState {
  override def handle: String = "Set Mode"
}

case class MoveModeState() extends GameState {
  override def handle: String = "Move Mode"
}

case class FlyModeState() extends GameState {
  override def handle: String = "FlyMode"
}

object RoundState {
  var roundState = SetModeState().handle
  def handle(e:RoundState):String = {
    e match {
      case SetModeState() => roundState = SetModeState().handle
      case MoveModeState() => roundState = MoveModeState().handle
      case FlyModeState() => roundState = FlyModeState().handle
    }
    roundState
  }
}
