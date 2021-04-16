package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.mill.controller.controllerBaseImpl.RoundManagerController

import scala.concurrent.ExecutionContextExecutor

case object RoundManagerHttpServer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "roundmanager")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val roundManagerController = new RoundManagerController()

    val interface: String = "localhost"
    val port: Int = 8082

    val route =
      concat(
        path("mgr") {
          get {
            parameters(pdef1 = "round") {
              _ => complete(HttpEntity(ContentTypes.`application/json`, roundManagerController.getRound))
            } ~
              parameters(pdef1 = "blackturn") {
                _ => complete(HttpEntity(ContentTypes.`application/json`, roundManagerController.blackTurn))
              } ~
              parameters(pdef1 = "whiteturn") {
                _ => complete(HttpEntity(ContentTypes.`application/json`, roundManagerController.whiteTurn))
              }
          }
        }
      )

    val bindingFuture = Http().newServerAt(interface, port).bind(route)

    println(s"Server online at https://$interface:$port")
  }
}
