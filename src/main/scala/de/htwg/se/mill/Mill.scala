package de.htwg.se.mill

import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.aview.gui.GUI
import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface}

import scala.io.StdIn.readLine

object Mill {
  val defaultsize = 7
  val injector: Injector = Guice.createInjector(new MillModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new GUI(controller)
  controller.createEmptyField(defaultsize)

  def main(args: Array[String]): Unit = {
    var input:String = ""

    if(args.length>0) input = args(0)
    if(input.nonEmpty) {tui.execInput(input) }
    else {
      do {
        print("Possible commands: new, random, place <location,0/1>, undo, redo, exit  -->\n")
        input = readLine()
        print(s"${tui.execInput(input).get}\n")
      } while (input != "exit")
    }
  }
}
