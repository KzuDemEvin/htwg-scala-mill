package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.mill.controller.FileIOControllerInterface

import scala.concurrent.Future

class FileIOHttpServer(controller: FileIOControllerInterface) {
  implicit val system = ActorSystem(Behaviors.empty, "fileIo")
  implicit val executionContext = system.executionContext

  val interface: String = "0.0.0.0"
  val port: Int = 8082
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
      } ~
        path(uriPath / "sqldb") {
          get {
            parameters("id") {
              id => complete(HttpEntity(ContentTypes.`application/json`, controller.loadSqlDb(id.toInt)))
            } ~
              complete(HttpEntity(ContentTypes.`application/json`, controller.toJson(controller.loadSqlDb())))
          } ~
          post {
            entity(as[String]) { fieldInJson =>
              controller.saveSqlDb(fieldInJson)
              complete("")
            }
          } ~
          delete {
            parameters("id") {
              id => {
                controller.deleteInSqlDB(id.toInt)
                complete("")
              }
            }
          }
        } ~
        path("") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Player Server</h1>"))
        }
    )
  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  println(s"FileIOs server is online at http://${interface}:${port}/${uriPath}")
}
