package de.htwg.se.mill.controller

import de.htwg.se.mill.model.RoundManager

trait RoundManagerControllerInterface {
  def blackTurn: String

  def whiteTurn: String

  def getRound: String
}
