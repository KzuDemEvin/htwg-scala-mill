import de.htwg.se.mill.model.Stone

val stone1 = Stone(0, "white")
val stone2 = Stone(1, "black")
val stone3 = Stone(0, "pink")


case class Field(stones: Vector[Stone])

val playground = Vector[Stone]()
playground ++ Seq(stone1, stone2, stone3)

val colorStone1 = stone1.whichColor
val isSet = stone1.value
val stoneB = Stone(0, "black")
printf("Color stone1: %s", colorStone1)

val gameboard1 =
    """|
       |0--0--0
       |0     0
       |0--0--0
       |""".stripMargin
val gameboard2 =
    """|
       |0----0----0
       || 0--0--0 |
       |0-0     0-0
       || 0--0--0 |
       |0----0----0
       |""".stripMargin
  val gameboard3 =
    """|
       |0------0------0
       || 0----0----0 |
       || | 0--0--0 | |
       |0-0-0     0-0-0
       || | 0--0--0 | |
       || 0----0----0 |
       |0------0------0
       |""".stripMargin
  val gameboard4 =
    """|
       |0--------0--------0
       || 0------0------0 |
       || | 0----0----0 | |
       || | | 0--0--0 | | |
       |0-0-0-0     0-0-0-0
       || | | 0--0--0 | | |
       || | 0----0----0 | |
       || 0------0------0 |
       |0--------0--------0
       |""".stripMargin