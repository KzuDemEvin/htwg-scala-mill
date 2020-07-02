package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.aview.gui.GUI
import de.htwg.se.mill.controller.controllerComponent.CellChanged
import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field

import scala.io.StdIn.readLine

object Mill {
  val defaultsize = 7
  val controller = new Controller(new Field(defaultsize))
  val tui = new Tui(controller)
  val gui = new GUI(controller)
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    var input:String = ""

    if(args.length>0) input = args(0)
    if(!input.isEmpty) {tui.execInput(input) }
    else {
      do {
        printf("Possible commands: new, random, place <location,0/1>, undo, redo, exit  -->")
        input = readLine()
        println(tui.execInput(input).get)
      } while (input != "exit")
    }
  }
}
