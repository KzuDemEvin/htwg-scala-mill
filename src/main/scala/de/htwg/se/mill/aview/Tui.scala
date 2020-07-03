package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.{CellChanged, Controller, GameState, WhiteTurnState}
import de.htwg.se.mill.model.{Cell, Stone}
import de.htwg.se.mill.util.Observer

import scala.swing.Reactor
import scala.util.{Failure, Success, Try}


class Tui(controller: Controller) extends Reactor {

  listenTo(controller)
  val size = 7

  def execInput(input: String): Try[String] = {
    input match {
      case "new" => controller.createEmptyField(size)
        Success("valid command: " + input)
      case "random" => controller.createRandomField(size)
        Success("valid command: " + input)
      case "undo" => controller.undo
        Success("valid command: " + input)
      case "redo" => controller.redo
        Success("valid command: " + input)
      case "exit" =>
        Success(input)
      case _ => input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
        case rowOld :: columnOld :: rowNew :: columnNew :: Nil => controller.moveStone(rowOld, columnOld, rowNew, columnNew)
          Success("valid command: " + input)
        case row :: column :: Nil => controller.set(row, column)
          //controller.checkMill(0)
          println(controller.millState)
          Success("valid command: " + input)
      }
      case _ =>
        Failure(new IllegalArgumentException("Wrong input: " + input))
    }
  }

  reactions += {
    case event: CellChanged => printTui
  }

  def printTui: Unit = {
    println(controller.fieldToString)
    println(GameState.state)
  }
//  override def update: Boolean = {
//    println(controller.fieldToString)
//    println(GameState.state)
//    GameState.handle(InProgessState())
//    true
//  }
}
