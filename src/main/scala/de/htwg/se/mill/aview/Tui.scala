package de.htwg.se.mill.aview

import de.htwg.se.mill.model._

class Tui {

  def exeInputLine(input: String, playground:Field):Field = {
    input match {
      case "quit" => playground
      case "new " => new Field(3)
      case "random" => new FieldCreator(3).fillRandomly(6)
      //case "white stone" =>
      //case "exit" => System.exit(0)
      case _ => throw new IllegalArgumentException("Invalid arguments!")
    }
  }
}
