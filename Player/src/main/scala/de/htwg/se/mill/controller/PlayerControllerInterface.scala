package de.htwg.se.mill.controller

import de.htwg.se.mill.model.playerComponent.Player

trait PlayerControllerInterface {
  def createPlayer(number: Int, name: String): Player

  def getPlayer(number: Int): Player

  def updatePlayerMode(number: Int, mode: String): Player

  def deletePlayer(number: Int): Player

  def toJson(player: Player): String
}
