package de.htwg.se.mill.controller.controllerComponent

trait GameState {
  def handle:String
}

case class FinishedState() extends GameState {
  override def handle: String = "Game finished"
}

case class WhiteTurnState() extends GameState {
  override def handle: String = "White's turn"
}

case class BlackTurnState() extends GameState {
  override def handle: String = "Black's turn"
}

case class NewState() extends GameState {
  override def handle: String = "New field"
}

case class RandomState() extends GameState {
  override def handle: String = "New field filled with random stones"
}

case class UndoState() extends GameState {
  override def handle: String = "Undo"
}

case class RedoState() extends GameState {
  override def handle: String = "Redo"
}

case class SaveState() extends GameState {
  override def handle: String = "Game saved"
}

case class LoadState() extends GameState {
  override def handle: String = "Game loaded"
}



object GameState {
  var state: String = NewState().handle
  def handle(e: GameState): String = {
    state = e match {
      case FinishedState() => FinishedState().handle
      case WhiteTurnState() => WhiteTurnState().handle
      case BlackTurnState() => BlackTurnState().handle
      case NewState() => NewState().handle
      case RandomState() => RandomState().handle
      case UndoState() => UndoState().handle
      case RedoState() => RedoState().handle
      case SaveState() => SaveState().handle
      case LoadState() => LoadState().handle
    }
    state
  }

  def whichState(state: String): GameState = {
    state match {
      case "White" | "white" | "White's turn" => WhiteTurnState()
      case "Black" | "black" | "Black's turn" => BlackTurnState()
    }
  }
}
