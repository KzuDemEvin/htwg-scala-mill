package de.htwg.se.mill.controller.controllerBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.PlayerModule
import de.htwg.se.mill.controller.PlayerControllerInterface
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.playerComponent.Player
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

class PlayerController extends PlayerControllerInterface {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val injector: Injector = Guice.createInjector(new PlayerModule)
  var daoInterface: PlayerDaoInterface = injector.instance[PlayerDaoInterface](Names.named("mongo"))
  var player1: Player = Player(name = "No name")
  var player2: Player = Player(name = "No name")
  val gson = new Gson

  override def changeSaveMethod(method: String): Unit = {
    printf(s"Changing saving method from ${daoInterface.toString} to $method\n")
    method match {
      case "mongo" => daoInterface = injector.instance[PlayerDaoInterface](Names.named("mongo"))
      case _ => daoInterface = injector.instance[PlayerDaoInterface](Names.named("sql"))
    }
  }

  private def setPlayer(number: Int, player: Player): Player = {
    if (number % 2 == 0) {
      player1 = player
    } else {
      player2 = player
    }
    player
  }

  override def createPlayer(number: Int, name: String): Player = {
    print(s"Creating Player ${number}!\n")
    setPlayer(number, Player(name))
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
    setPlayer(number, Player.apply("No name"))
  }

  override def save(number: Int): Unit = daoInterface.save(getPlayer(number))

  override def load(id: Int, number: Int): Unit = {
    daoInterface.load(id).onComplete {
      case Success(player) => {
        player match {
          case (id: Int, name: String, amountStones: Int, mode: String) => {
            val tmp: Player = Player.apply(name, amountStones).changeMode(mode)
            setPlayer(number, tmp)
          }
          case (jsonString: String) => {
            val json: JsValue = Json.parse(jsonString)
            val name = (json \ "name").get.toString().replaceAll("\"", "")
            val amountStones = (json \ "amountStones").get.toString().toInt
            val mode = (json \ "mode").get.toString().replaceAll("\"", "")
            setPlayer(number, Player.apply(name, amountStones).changeMode(mode))
          }
        }
      }
      case Failure(_) => print(s"Something went wrong when loading player ${number}!\n")
    }
  }

  override def load(): Map[Int, Player] = daoInterface.load()

  override def toJson(player: Player): String = gson.toJson(player)

  override def toJson(players: Map[Int, Player]): String = gson.toJson(players)
}
