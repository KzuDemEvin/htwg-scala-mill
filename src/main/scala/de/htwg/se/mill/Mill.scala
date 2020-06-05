package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{FieldCreator}

import scala.io.StdIn.readLine

object Mill {
  val controller = new Controller(new FieldCreator().createField(7))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input:String = ""

    do {
      printf("Possible commands: new, random, white, black, exit  -->")
      input = readLine()
      tui.execInput(input)
    } while (input != "exit")
  }

}
