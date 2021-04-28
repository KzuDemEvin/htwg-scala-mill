package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.{Http, server}
import de.htwg.se.mill.controller.controllerRoundManager.RoundManagerControllerInterface

import scala.concurrent.ExecutionContextExecutor

class RoundManagerHttpServer(roundManagerController: RoundManagerControllerInterface) {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "roundmanager")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val interface: String = "localhost"
  val port: Int = 8083

  val route: Route =
    concat(
      path("undo") {
        post {
          postResponse(roundManagerController.undo())
        }
      } ~
      path("handleClick") {
        post {
          parameters("row", "col") {
            (row, col) => postResponse(roundManagerController.handleClick(row.toInt, col.toInt))
          }
        }
      } ~
        pathPrefix("field") {
          path("setField") {
            post {
              entity(as[String]) { fieldInJson =>
                postResponse(roundManagerController.setField(fieldInJson))
              }
            }
          }~
          path("json") {
            get {
              postResponse(roundManagerController.fieldAsJson())
            }
          } ~
            path("html") {
              get {
                postResponse(roundManagerController.fieldAsHtml())
              }
            } ~
            path("string") {
              get {
                postResponse(roundManagerController.fieldAsString())
              }
            } ~
            path("createRandomField") {
              post {
                parameters("size") {
                  size => postResponse(roundManagerController.createRandomField(size.toInt))
                }
              }
            } ~
            path("createField") {
              post {
                parameters("size") {
                  size => postResponse(roundManagerController.createEmptyField(size.toInt))
                }
              }
            } ~
            path("isSet") {
              get {
                parameters("row", "col") {
                  (row, col) => postResponse(roundManagerController.isSet(row.toInt, col.toInt))
                }
              }
            } ~
            path("color") {
              get {
                parameters("row", "col") {
                  (row, col) => postResponse(roundManagerController.color(row.toInt, col.toInt))
                }
              }
            } ~
            path("possiblePosition") {
              get {
                parameters("row", "col") {
                  (row, col) => postResponse(roundManagerController.possiblePosition(row.toInt, col.toInt))
                }
              }
            } ~
            path("millState") {
              get {
                postResponse(roundManagerController.millState())
              }
            }
        } ~
        path("turn") {
          get {
            postResponse(roundManagerController.turn())
          }
        } ~
        path("roundCounter") {
          get {
            postResponse(roundManagerController.roundCounter())
          }
        } ~
        path("winner") {
          get {
            postResponse(roundManagerController.winner())
          }
        } ~
        path("winnerText") {
          get {
            postResponse(roundManagerController.winnerText())
          }
        }
    )

  val bindingFuture = Http().newServerAt(interface, port).bind(route)

  print(s"RoundManager Server is online at http://$interface:$port/\n")

  def postResponse(string: String): server.Route = {
    complete(HttpEntity(ContentTypes.`application/json`, string))
  }
}
