package de.htwg.se.mill.controller

trait GameState {
  def handle:String
}

case class InProgessState() extends GameState {
  override def handle: String = "Game in progress"
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

case class SetState() extends GameState {
  override def handle: String = "Set"
}

case class UndoState() extends GameState {
  override def handle: String = "Undo"
}

case class RedoState() extends GameState {
  override def handle: String = "Redo"
}

case class BlackMillState() extends GameState {
  override def handle: String = "Black Mill"
}

case class WhiteMillState() extends GameState {
  override def handle: String = "White Mill"
}

object GameState {
  var state = InProgessState().handle
  def handle(e: GameState): String = {
    e match {
      case InProgessState() => state = InProgessState().handle
      //case FinishedState() => state = FinishedState().handle
      case WhiteTurnState() => state = WhiteTurnState().handle
      case BlackTurnState() => state = BlackTurnState().handle
      case NewState() => state = NewState().handle
      case UndoState() => state = UndoState().handle
      case RedoState() => state = RedoState().handle
      case WhiteMillState() => state = WhiteMillState().handle
      case BlackMillState() => state = BlackMillState().handle
    }
    state
  }
}
