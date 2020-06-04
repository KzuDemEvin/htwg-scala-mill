package de.htwg.se.mill

import de.htwg.se.mill.model.{FieldCreator, Player, Stone}
import de.htwg.se.mill.aview.Tui

import scala.io.StdIn.readLine

object Mill {
  def main(args: Array[String]): Unit = {
    val student = new Player("Kevin")
    println("Hello, " + student.name)
    val gameboard = printGameboard()
    printf("Gameboard for mill:\n")
    printf(gameboard)
    printf("%d", student.amountStones)
    val gb = new FieldCreator().createField(3)
    printf(gb.toString)
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
    return gb
  }
}
