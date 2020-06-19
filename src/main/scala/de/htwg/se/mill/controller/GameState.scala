package de.htwg.se.mill.controller

object GameState extends Enumeration {
    type GameState = Value
    val INPROGRESS, FINISHED = Value

    val map: Map[GameState, String] = Map[GameState, String](
      INPROGRESS -> "Game still in progress",
      FINISHED ->"Game ended")

    def message(gameState: GameState): String = {
      map(gameState)
    }
}
