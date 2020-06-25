package de.htwg.se.mill.controller

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

object GameState {
  var state = NewState().handle
  def handle(e: GameState): String = {
    e match {
      case FinishedState() => state = FinishedState().handle
      case WhiteTurnState() => state = WhiteTurnState().handle
      case BlackTurnState() => state = BlackTurnState().handle
      case NewState() => state = NewState().handle
      case RandomState() => state = RandomState().handle
      case UndoState() => state = UndoState().handle
      case RedoState() => state = RedoState().handle
    }
    state
  }
}
