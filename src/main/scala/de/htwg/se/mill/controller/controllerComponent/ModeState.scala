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

  def handle(e:ModeState):String = {
    e match {
      case MoveModeState() => MoveModeState().handle
      case FlyModeState() => FlyModeState().handle
      case _ => SetModeState().handle
    }
  }

  def whichState(s:String):ModeState = {
    s match {
      case "SetMode" => SetModeState().whichState
      case "FlyMode" => FlyModeState().whichState
      case _ => MoveModeState().whichState
    }
  }
}
