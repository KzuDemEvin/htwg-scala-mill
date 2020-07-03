package de.htwg.se.mill.controller.controllerComponent

trait ModeState {
  def handle:String
  def whichState:ModeState
}

case class SetModeState() extends ModeState {
  override def handle: String = "SetMode"
  override def whichState:ModeState = SetModeState()
}

case class MoveModeState() extends ModeState {
  override def handle: String = "MoveMode"
  override def whichState:ModeState = MoveModeState()
}

case class FlyModeState() extends ModeState {
  override def handle: String = "FlyMode"
  override def whichState:ModeState = FlyModeState()
}

object ModeState {
  var modeState = SetModeState().handle
  var cmd = MoveModeState().whichState

  def handle(e:ModeState):String = {
    e match {
      case SetModeState() => modeState = SetModeState().handle
      case MoveModeState() => modeState = MoveModeState().handle
      case FlyModeState() => modeState = FlyModeState().handle
    }
    modeState
  }

  def whichState(s:String):ModeState = {
    s match {
      case "SetMode" => cmd = SetModeState().whichState
      case "MoveMode" => cmd = MoveModeState().whichState
      case "FlyMode" => cmd = FlyModeState().whichState
    }
    cmd
  }
}
