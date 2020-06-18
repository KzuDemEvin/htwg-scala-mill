package de.htwg.se.mill.controller

object GameState extends Enumeration {
    type GameState = Value
    val IDLE, FINISHED = Value

    val map: Map[GameState, String] = Map[GameState, String](
      IDLE -> "",
      FINISHED ->"Game ended")

    def message(gameState: GameState): String = {
      map(gameState)
    }
}
