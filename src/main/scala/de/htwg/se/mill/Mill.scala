package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.model.Field
import scala.io.StdIn.readLine

object Mill {


  def main(args: Array[String]): Unit = {
    val tui = new Tui
    var field = new Field(3)
    var input:String = ""

    do {
      val gb = printGameboard()
      printf(gb)
      print("possible commands: new, random, quit, exit  -->")
      input = readLine()
      field = tui.exeInputLine(input, field)
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
