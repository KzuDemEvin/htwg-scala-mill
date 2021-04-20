package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.mill.controller.FileIOControllerInterface

class FileIOHttpServer(controller: FileIOControllerInterface) {
  implicit val system = ActorSystem(Behaviors.empty, "fileIo")
  implicit val executionContext = system.executionContext

  val interface: String = "localhost"
  val port: Int = 9002
  val uriPath: String = "json"

  val route =
    concat (
      path(uriPath) {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, controller.load(None)))
        } ~
        post {
          entity(as[String]) { fieldInJson =>
            controller.save(fieldInJson, None)
            complete("")
          }
        }
      }
    )
  val bindingFuture = Http().newServerAt(interface, port).bind(route)

  println(s"FileIOs server is online at http://${interface}:${port}/${uriPath}")
}
