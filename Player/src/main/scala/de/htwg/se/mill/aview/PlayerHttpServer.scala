package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.{Http, server}
import de.htwg.se.mill.controller.PlayerControllerInterface

import scala.concurrent.{ExecutionContextExecutor, Future}

class PlayerHttpServer(playerController: PlayerControllerInterface) {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "player")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val interface: String = "0.0.0.0"
  val port: Int = 8081
  val uriPath: String = "player"

  val route: Route =
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
        path(uriPath / "name") {
          get {
            parameters("number") {
              number => postResponse(playerController.getPlayer(number.toInt).name)
            }
          }
        } ~
        path(uriPath / "db") {
          get {
            parameters("id", "number") {
              (id, number) => postResponse(playerController.toJson(playerController.load(id.toInt, number.toInt)))
            }
          } ~
          post {
            parameters("number") {
              number => {
                playerController.save(number.toInt)
                postResponse("Player saved!")
              }
            }
          } ~
          put {
            parameters("type") {
              dbType => {
                playerController.changeSaveMethod(dbType)
                postResponse("Database changed!")
              }
            }
          }
        } ~
        path("") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Player Server</h1>"))
        }
    )

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  println(s"Players server is online at http://$interface:$port/$uriPath")

  def postResponse(player: String): server.Route = {
    complete(HttpEntity(ContentTypes.`application/json`, player))
  }
}
