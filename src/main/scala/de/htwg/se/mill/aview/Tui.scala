package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface, GameState}

import scala.swing.Reactor
import scala.util.{Failure, Success, Try}


class Tui(controller: ControllerInterface) extends Reactor {

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
      case "save" => controller.save
        Success("valid command: " + input)
      case "load" => controller.load
        Success("valid command: " + input)
      case "exit" =>
        Success(input)
      case _ => input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
        case row :: column :: Nil => controller.handleClick(row, column)
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
}
