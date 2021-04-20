package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.{Http, server}
import de.htwg.se.mill.controller.PlayerControllerInterface

class PlayerHttpServer(playerController: PlayerControllerInterface) {
    implicit val system = ActorSystem(Behaviors.empty, "player")
    implicit val executionContext = system.executionContext

    val interface: String = "localhost"
    val port: Int = 9001
    val uriPath: String = "player"

    val route =
      concat(
        path(uriPath) {
          get {
            parameters("number") {
              number => postResponse(playerController.toJson(playerController.getPlayer(number.toInt)))
            }
          } ~
          post {
            parameters("number", "name") {
              (number, name) => postResponse(playerController.toJson(playerController.createPlayer(number.toInt, name)))
            }
          } ~
          put {
            parameters("number", "mode") {
              (number, mode) => postResponse(playerController.toJson(playerController.updatePlayerMode(number.toInt, mode)))
            }
          } ~
          delete {
            parameters("number") {
              number => postResponse(playerController.toJson(playerController.deletePlayer(number.toInt)))
            }
          }
        }
      )

  val bindingFuture = Http().newServerAt(interface, port).bind(route)

  println(s"Players server is online at http://${interface}:${port}/${uriPath}")

  def postResponse(player: String): server.Route = {
    complete(HttpEntity(ContentTypes.`application/json`, player))
  }
}
