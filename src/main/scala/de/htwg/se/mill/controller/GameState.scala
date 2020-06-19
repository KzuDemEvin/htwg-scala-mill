package de.htwg.se.mill.controller

trait Event

case class InProgessEvent() extends Event

case class FinishedEvent() extends Event

case class WhiteTurnEvent() extends Event

case class BlackTurnEvent() extends Event

object GameState {
  var state = inProgress
  def handle(e: Event): String = {
    e match {
      case p: InProgessEvent => state = inProgress
      case f: FinishedEvent => state = finished
      case w: WhiteTurnEvent => state = WhiteTurn
      case b: BlackTurnEvent => state = BlackTurn
    }
    state
  }

  def inProgress: String = "Game in progress"
  def finished: String = "Game finished"
  def WhiteTurn: String = "White's turn"
  def BlackTurn: String = "Black's turn"
}
