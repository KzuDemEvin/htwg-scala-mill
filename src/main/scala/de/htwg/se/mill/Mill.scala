package de.htwg.se.mill

import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.aview.gui.GUI
import de.htwg.se.mill.aview.{HttpServer, Tui}
import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import java.awt.GraphicsEnvironment
import scala.io.StdIn.readLine

object Mill {
  val defaultSize = 7
  val injector: Injector = Guice.createInjector(new MillModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  if (!GraphicsEnvironment.isHeadless) {
    val gui = new GUI(controller)
  }
  val webserver: HttpServer = HttpServer(controller)
  var input: String = _
  controller.createEmptyField(defaultSize)

  def main(args: Array[String]): Unit = {

    if(args.length>0) input = args(0)
    if(input.nonEmpty) {tui.execInput(input) }
    else {
      do {
        if (Console.in.ready()) {
          print("Possible commands: new, random, place <location,0/1>, undo, redo, exit  -->\n")
          input = readLine()
          print(s"${tui.execInput(input)}\n")
        } else {
          input = ""
        }
      } while (input != "exit")
      webserver.unbind()
    }
  }
}
