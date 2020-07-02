package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.CommandChoice

trait ModeState {
  def handle:String
  def whichDriveCommand:CommandChoice.Value
}

case class SetModeState() extends ModeState {
  override def handle: String = "SetMode"
  override def whichDriveCommand:CommandChoice.Value = CommandChoice.set
}

case class MoveModeState() extends ModeState {
  override def handle: String = "MoveMode"
  override def whichDriveCommand:CommandChoice.Value = CommandChoice.move
}

case class FlyModeState() extends ModeState {
  override def handle: String = "FlyMode"
  override def whichDriveCommand:CommandChoice.Value = CommandChoice.fly
}

object ModeState {
  var modeState = SetModeState().handle
  var cmd = SetModeState().whichDriveCommand

  def handle(e:ModeState):String = {
    e match {
      case SetModeState() => modeState = SetModeState().handle
      case MoveModeState() => modeState = MoveModeState().handle
      case FlyModeState() => modeState = FlyModeState().handle
    }
    modeState
  }

  def whichDriveCommand(s:String):CommandChoice.Value = {
    s match {
      case "SetMode" => cmd = SetModeState().whichDriveCommand
      case "MoveMode" => cmd = MoveModeState().whichDriveCommand
      case "FlyMode" => cmd = FlyModeState().whichDriveCommand
    }
    cmd
  }
}
