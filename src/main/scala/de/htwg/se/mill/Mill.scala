package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{Field, FieldCreator}

import scala.io.StdIn.readLine

object Mill {
  val controller = new Controller(new Field(7))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input:String = ""
    print("possible commands: new, random, white, black, exit  -->")

    do {
      input = readLine()
      tui.execInput(input)
    } while (true)
  }

  def printGameboard(): String = {
    val gb =
      """|
         |0------0------0
         || 0----0----0 |
         ||   0--0--0   |
         |0   0     0   0
         ||   0--0--0   |
         || 0----0----0 |
         |0------0------0
         |""".stripMargin
    gb
  }
}
