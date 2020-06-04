package de.htwg.se.mill.aview

import de.htwg.se.mill.model._
import scala.sys

class Tui {


  def exeInputLine(input: String, playground:Field):Field = {
    input match {
      case "new" => new Field(3)
      case "random" => new FieldCreator().createField(7).fillRandomly(6)
      //case "white stone" =>
      case "quit" => playground
      case "exit" => sys.exit(0)
      case _ => throw new IllegalArgumentException("Invalid arguments!")
    }
  }
}
