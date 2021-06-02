package de.htwg.se.mill.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import scala.concurrent.{ExecutionContextExecutor, Future}

case class HttpServer(controller: ControllerInterface) {
  val size: Int = 7

  implicit val system: ActorSystem = ActorSystem("mill")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val interface: String = "0.0.0.0"
  val port: Int = 8080
  val uriPath: String = "mill"

  val route: Route =
    concat(
      path(uriPath) {
        get {
          fieldToHtml
        }
      } ~
        path(uriPath / "player") {
          get {
            parameters("name", "number") {
              (name, number) =>
                controller.createPlayer(name, number.toInt)
                fieldToHtml
            }
          }
        } ~
        path(uriPath / "new") {
          get {
            controller.createEmptyField(size)
            fieldToHtml
          }
        } ~
        path(uriPath / "random") {
          get {
            controller.createRandomField(size)
            fieldToHtml
          }
        } ~
        path(uriPath / "changeSaveMethod") {
          put {
            parameters("type") {
              dbType => {
                controller.changeSaveMethod(dbType)
                complete(HttpEntity(ContentTypes.`application/json`, "Database changed!"))
              }
            }
          }
        },
      path(uriPath / "save") {
        get {
          controller.save()
          fieldToHtml
        }
      } ~
        path(uriPath / "save" / "db") {
          get {
            controller.saveDB()
            fieldToHtml
          }
        } ~
        path(uriPath / "load") {
          get {
            controller.load()
            fieldToHtml
          }
        } ~
        path(uriPath / "load" / "db") {
          get {
            parameters("id") {
              id =>
                controller.loadDB(id.toInt)
                fieldToHtml
            }
          }
        } ~
        path(uriPath / "undo") {
          get {
            controller.undo()
            fieldToHtml
          }
        } ~
        path(uriPath / "redo") {
          get {
            controller.redo()
            fieldToHtml
          }
        } ~
        path(uriPath / Segment) { command => {
          get {
            processInputLine(command)
            fieldToHtml
          }
        }
        }
    )

  def fieldToHtml: StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, standardHtml))
  }

  def standardHtml: String = {
    s"""<head>
       |  <meta charset=utf-8>
       |  <meta http-equip=X-UA-Compatible content="IE=edge">
       |  <meta name=viewport content="width=device-width,inital-scale=1">
       |  <meta name=author content="Kevin, Josef">
       |  <meta name=description content="The best Mill game you've ever seen">
       |  <title>HTWG Mill</title>
       |</head>
       |<body>
       |<style>
       |  * {
       |    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
       |  }
       |
       |  body {
       |    font-size: 2em;
       |    font-weight: 500;
       |    line-height: 1.4;
       |    max-width: 28em;
       |    margin: auto;
       |    width: 50%;
       |    text-align: center;
       |  }
       |
       |  button {
       |    border: none;
       |  }
       |</style>
       |<script>
       | function process () {
       |   const value = document.getElementById('input').value
       |   if (value) {
       |     window.location='/mill/' + value
       |   } else {
       |     alert("Input required!")
       |   }
       | }
       |
       | window.onload = () => {
       |   document.getElementById('input').addEventListener('keypress', (e) => {
       |     if (e.key === 'Enter' || e.keyCode === 13) {
       |       process()
       |     }
       |   })
       | }
       |</script>
       | <h1>HTWG Mill</h1>
       | <div>
       |   <label for="input"/>
       |   <input id="input" type="text" placeholder="Enter command" />
       |   <button id="confirm" onclick="process()">Confirm</button>
       |    ${controller.fieldToHtmlSync}
       | </div>
       |</body>
       |""".stripMargin
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  println(s"Mill server is online at http://$interface:$port/$uriPath")

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def processInputLine(input: String): Unit = {
    input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
      case row :: column :: Nil => controller.handleClick(row, column)({ case Some(_) => })
      case _ =>
    }
  }
}
