package de.htwg.se.mill

import de.htwg.se.mill.aview.Tui
import de.htwg.se.mill.model.{Player, Stone}

import scala.io.StdIn.readLine

object Mill {
  val student = Player("Kevin")
  //val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""
    printf("%s", student.amountStones)
//    do {
//
//      input = readLine()
//    } while (input != "q")



    println("Hello, " + student.name)
    val gameboard = printGameboard()
    printf("Gameboard for mill:\n")
    printf(gameboard)
  }

  def printGameboard(): String = {
    val gb =
      """|
         |0------0------0
         || 0----0----0 |
         || | 0--0--0 | |
         |0-0-0     0-0-0
         || | 0--0--0 | |
         || 0----0----0 |
         |0------0------0
         |""".stripMargin
    return gb
  }
}
