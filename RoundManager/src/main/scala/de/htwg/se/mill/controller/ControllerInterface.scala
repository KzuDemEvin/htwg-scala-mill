package de.htwg.se.mill.controller

import de.htwg.se.mill.model.RoundManager

trait ControllerInterface {
  def blackTurn: Boolean
  def whiteTurn: Boolean
  def setPlayer: RoundManager

}
