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

object GameState {
  var state = InProgessState().handle
  def handle(e: GameState): String = {
    e match {
      case InProgessState() => state = InProgessState().handle
      case FinishedState() => state = FinishedState().handle
      case WhiteTurnState() => state = WhiteTurnState().handle
      case BlackTurnState() => state = BlackTurnState().handle
    }
    state
  }
}
