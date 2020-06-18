package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.{Controller, GameState}
import de.htwg.se.mill.model.{Cell, Color, Stone}
import de.htwg.se.mill.util.Observer


class Tui(controller: Controller) extends Observer {

  controller.add(this)
  val size = 7
  val amountStones = 6

  def execInput(input: String):Unit = {
    input match {
      case "new" => controller.createEmptyField(size)
      case "random" => controller.createRandomField(size, amountStones)
      case "white" => controller.set(0, 0, Cell(true, Stone(1, Color.white)))
      case "black" => controller.set(1, 1, Cell(true, Stone(1, Color.black)))
      case "undo" => controller.undo
      case "redo" => controller.redo
//      case _ => input.toList.filter(p => p != ' ').filter(_.isDigit).map(p =>  p.toString.toInt) match {
//        case row :: column :: value :: Nil => value match {
//          case 0 => controller.set(row, column, Cell(true, Stone(1, Color.white)))
//          case _ => controller.set(row, column, Cell(true, Stone(1, Color.black)))
//        }
        case _ =>
    }
  }

  override def update: Boolean = {
    println(controller.fieldToString)
    println(GameState.message(controller.gameState))
    controller.gameState=GameState.IDLE
    true
  }
}
