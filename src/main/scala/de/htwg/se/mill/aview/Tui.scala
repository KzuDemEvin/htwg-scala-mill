package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface, GameState}

import scala.swing.Reactor
import scala.util.{Failure, Success, Try}


class Tui(controller: ControllerInterface) extends Reactor {

  listenTo(controller)
  val size = 7

  def execInput(input: String): String = {
    input match {
      case "new" =>
        controller.createEmptyField(size)
        "valid command: " + input
      case "random" =>
        controller.createRandomField(size)
        "valid command: " + input
      case "undo" =>
        controller.undo
        "valid command: " + input
      case "redo" =>
        controller.redo
        "valid command: " + input
      case "save" =>
        controller.save
        "valid command: " + input
      case "load" =>
        controller.load
        "valid command: " + input
      case "exit" =>
        "valid command: " + input
      case _ =>
        input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
        case row :: column :: Nil =>
          controller.handleClick(row, column)
          println(controller.millState)
          "valid command: " + input
        case _ =>
          "Wrong input: " + input
      }
    }
  }

  reactions += {
    case event: CellChanged => printTui
  }

  def printTui: Unit = {
    printf(s"${controller.fieldToString}\n")
    printf(s"$GameState.state}\n")
  }
}
