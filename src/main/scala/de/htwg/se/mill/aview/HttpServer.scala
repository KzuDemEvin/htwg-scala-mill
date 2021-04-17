package de.htwg.se.mill.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, ServerBuilder}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import scala.concurrent.Future

case class HttpServer(controller: ControllerInterface) {
  val size: Int = 7

  implicit val system = ActorSystem("mill")
  implicit val executionContext = system.dispatcher

  val interface: String = "localhost"
  val port: Int = 8080

  val route: Route =
    concat(
    path("mill") {
      get {
        gridToHtml
      }
    } ~
      path("mill" / "new") {
        get {
          controller.createEmptyField(size)
          gridToHtml
        }
      } ~
      path("mill" / "random") {
        get {
          controller.createRandomField(size)
          gridToHtml
        }
      } ~
      path("mill" / "undo") {
        get {
          controller.undo
          gridToHtml
        }
      } ~
      path("mill" / "redo") {
        get {
          controller.redo
          gridToHtml
        }
      } ~
      path("mill" / Segment) { command => {
        get {
          processInputLine(command)
          gridToHtml
        }
      }
      } ~
      path("mill" / "removeStone") {
        get {
          parameters("row", "col", "color") {
            (row, col, color) =>
              complete(HttpEntity(ContentTypes.`application/json`, controller.stoneHasOtherColorREST(row.toInt, col.toInt, color)))
          }
        }
      }
  )

  def gridToHtml: StandardRoute = {
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
       |    ${controller.fieldToHtml}
       | </div>
       |</body>
       |""".stripMargin
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

  def processInputLine(input: String): Unit = {
    input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
      case row :: column :: Nil => controller.handleClick(row, column)
      case _ =>
    }
  }
}
