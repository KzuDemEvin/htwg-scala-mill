package de.htwg.se.mill

import de.htwg.se.mill.model.{Player, Stone}

object Mill {
  def main(args: Array[String]): Unit = {
    val student = Player("Kevin")
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
         ||   0--0--0   |
         |0   0     0   0
         ||   0--0--0   |
         || 0----0----0 |
         |0------0------0
         |""".stripMargin
    return gb
  }
}
