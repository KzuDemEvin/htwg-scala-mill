package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import de.htwg.se.mill.controller.PlayerControllerInterface
import de.htwg.se.mill.model.playerComponent.Player

class PlayerController extends PlayerControllerInterface {
  var player1: Player = Player(name = "No name")
  var player2: Player = Player(name = "No name")

  override def createPlayer(number: Int, name: String): Player = {
    print(s"Creating Player ${number}!\n")
    val player: Player = Player(name)
    if (number % 2 == 0) {
      player1 = player
    } else {
      player2 = player
    }
    player
  }

  override def getPlayer(number: Int): Player = {
    print(s"Player ${number} called!\n")
    if (number % 2 == 0) {
      player1
    } else {
      player2
    }
  }

  override def updatePlayerMode(number: Int, mode: String): Player = {
    print(s"Updating Playermode of Player ${number} to ${mode}!\n")
    if (number % 2 == 0) {
      player1 = player1.changeMode(mode)
      player1
    } else {
      player2 = player2.changeMode(mode)
      player2
    }
  }

  override def deletePlayer(number: Int): Player = {
    print(s"Deleting Player ${number} called!\n")
    if (number % 2 == 0) {
      player1 = Player(name = "No name")
      player1
    } else {
      player2 = Player(name = "No name")
      player2
    }
  }

  override def toJson(player: Player): String = {
    val gson = new Gson
    gson.toJson(player)
  }
}
