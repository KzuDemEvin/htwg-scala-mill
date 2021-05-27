package de.htwg.se.mill.controller

import de.htwg.se.mill.model.playerComponent.Player

trait PlayerControllerInterface {
  def changeSaveMethod(method: String): Unit

  def createPlayer(number: Int, name: String): Player

  def getPlayer(number: Int): Player

  def updatePlayerMode(number: Int, mode: String): Player

  def deletePlayer(number: Int): Player

  def save(number: Int): Unit

  def load(id: String, number: Int): Unit

  def load(): Map[Int, Player]

  def toJson(player: Player): String

  def toJson(players: Map[Int, Player]): String
}
