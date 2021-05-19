package de.htwg.se.mill.model

trait ModeState {
  def handle: String
}

case class SetModeState() extends ModeState {
  override def handle: String = "SetMode"
}

case class RemoveModeState() extends ModeState {
  override def handle: String = "RemoveMode"
}

case class MoveModeState() extends ModeState {
  override def handle: String = "MoveMode"
}

case class FlyModeState() extends ModeState {
  override def handle: String = "FlyMode"
}

object ModeState {

  def handle(e: ModeState): String = {
    e match {
      case MoveModeState() => MoveModeState().handle
      case FlyModeState() => FlyModeState().handle
      case RemoveModeState() => RemoveModeState().handle
      case _ => SetModeState().handle
    }
  }

  def whichState(s: String): ModeState = {
    s match {
      case "SetMode" => SetModeState()
      case "RemoveMode" => RemoveModeState()
      case "FlyMode" => FlyModeState()
      case _ => MoveModeState()
    }
  }
}
