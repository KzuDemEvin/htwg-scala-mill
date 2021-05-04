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

  val interface: String = "0.0.0.0"
  val port: Int = 8081
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
      } ~
        path(uriPath / "sqldb") {
          get {
            parameters("id") {
              id => postResponse(playerController.toJson(playerController.load(id.toInt)))
            } ~
              postResponse(playerController.toJson(playerController.load()))
          } ~
            post {
              parameters("number") {
                number => {
                  playerController.save(number.toInt)
                  postResponse("")
                }
              }
            }
        } ~
        path("") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Player Server</h1>"))
        }
    )

  val bindingFuture = Http().newServerAt(interface, port).bind(route)

  println(s"Players server is online at http://$interface:$port/$uriPath")

  def postResponse(player: String): server.Route = {
    complete(HttpEntity(ContentTypes.`application/json`, player))
  }
}
