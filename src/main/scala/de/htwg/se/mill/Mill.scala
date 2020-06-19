package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{Field}

import scala.io.StdIn.readLine

object Mill {
  val controller = new Controller(new Field(7))
  val tui = new Tui(controller)
  controller.notifyObservers

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
