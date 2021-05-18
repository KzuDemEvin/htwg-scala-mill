package de.htwg.se.mill.aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.mill.controller.FileIOControllerInterface

import scala.concurrent.{ExecutionContextExecutor, Future}

class FileIOHttpServer(controller: FileIOControllerInterface) {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "fileIo")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val interface: String = "0.0.0.0"
  val port: Int = 8082
  val uriPath: String = "fileio"

  val route =
    concat(
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
        path(uriPath / "db") {
          get {
            parameters("id") {
              id => complete(HttpEntity(ContentTypes.`application/json`, controller.loadDb(id)))
            } ~
              complete(HttpEntity(ContentTypes.`application/json`, controller.toJson(controller.loadAllDb())))
          } ~
            post {
              entity(as[String]) { fieldInJson =>
                controller.saveDb(fieldInJson)
                complete("Game saved!")
              }
            } ~
            put {
              parameters("type") {
                dbType => {
                  controller.changeSaveMethod(dbType)
                  complete(HttpEntity(ContentTypes.`application/json`, "Database changed!"))
                }
              }
            } ~
            delete {
              parameters("id") {
                id => {
                  controller.deleteInDB(id)
                  complete("Deleted!")
                }
              }
            }
        } ~
        path("") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>FileIO Server</h1>"))
        }
    )
  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  printf(s"FileIOs server is online at http://$interface:$port/$uriPath\n")
}
