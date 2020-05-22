case class Stone(value:Boolean, color:String) {
  def isSet:Boolean = value
  def whichColor:String = color
}

val stone1 = Stone(value = true, "white")
val stone2 = Stone(value = true, "black")
val stone3 = Stone(value = false, "white")


//case class Field(stones: Vector[Stone])

val playground = Vector[Stone]()
playground ++ Seq(stone1, stone2, stone3)

val colorStone1 = stone1.whichColor
printf("Color stone1: %s", colorStone1)

def printGameboard(): String = {
  val gameboard =
    """|
     |0------0------0
       || 0----0----0 |
       ||   0--0--0   |
       |0   0     0   0
       ||   0--0--0   |
       || 0----0----0 |
       |0------0------0
       |""".stripMargin
  return gameboard
}
val gb = printGameboard()

printf("%s", gb)