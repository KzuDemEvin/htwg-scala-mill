package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import de.htwg.se.mill.controller.PlayerControllerInterface

class PlayerHttpServer(playerController: PlayerControllerInterface) {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "player")
    implicit val executionContext = system.executionContext

    val interface: String = "localhost"
    val port: Int = 8081

    val route =
      concat(
        path("player") {
          get {
            parameters("number") {
              number => complete(playerController.toJson(playerController.getPlayer(number.toInt)))
            }
          }
          post {
            parameters("number", "name") {
              (number, name) => complete(playerController.toJson(playerController.createPlayer(number.toInt, name)))
            }
          }
          put {
            parameters("number", "mode") {
              (number, mode) => complete(playerController.toJson(playerController.updatePlayerMode(number.toInt, mode)))
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt(interface, port).bind(route)

    println(s"Server online at http://${interface}:${port}")
  }
}
