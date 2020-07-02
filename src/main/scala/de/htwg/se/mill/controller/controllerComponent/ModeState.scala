package de.htwg.se.mill.controller.controllerComponent

trait ModeState {
  def handle:String
}

case class SetModeState() extends ModeState {
  override def handle: String = "Set Mode"
}

case class MoveModeState() extends ModeState {
  override def handle: String = "Move Mode"
}

case class FlyModeState() extends ModeState {
  override def handle: String = "FlyMode"
}

object ModeState {
  var modeState = SetModeState().handle
  def handle(e:ModeState):String = {
    e match {
      case SetModeState() => modeState = SetModeState().handle
      case MoveModeState() => modeState = MoveModeState().handle
      case FlyModeState() => modeState = FlyModeState().handle
    }
    modeState
  }
}
