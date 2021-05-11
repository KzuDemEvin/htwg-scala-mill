package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.PlayerModule
import de.htwg.se.mill.controller.PlayerControllerInterface
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.playerComponent.Player
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

class PlayerController extends PlayerControllerInterface {
  val injector: Injector = Guice.createInjector(new PlayerModule)
  var daoInterface: PlayerDaoInterface = injector.instance[PlayerDaoInterface](Names.named("sql"))
  var player1: Player = Player(name = "No name")
  var player2: Player = Player(name = "No name")
  val gson = new Gson

  override def changeSaveMethod(method: String): Unit = {
    method match {
      case "mongo" => daoInterface = injector.instance[PlayerDaoInterface](Names.named("mongo"))
      case _ => daoInterface = injector.instance[PlayerDaoInterface](Names.named("sql"))
    }
  }

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

  override def save(number: Int): Unit = daoInterface.save(getPlayer(number))

  override def load(id: Int): Player = {
    daoInterface.load(id)
  }

  override def load(): Map[Int, Player] = daoInterface.load()

  override def toJson(player: Player): String = gson.toJson(player)

  override def toJson(players: Map[Int, Player]): String = gson.toJson(players)
}
