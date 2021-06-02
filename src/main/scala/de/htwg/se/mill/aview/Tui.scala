package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.controllerComponent.{ControllerInterface, GameState}

import scala.swing.Reactor


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
        controller.undo()
        "valid command: " + input
      case "redo" =>
        controller.redo()
        "valid command: " + input
      case "save" =>
        controller.save()
        "valid command: " + input
      case "load" =>
        controller.load()
        "valid command: " + input
      case "exit" =>
        "valid command: " + input
      case _ =>
        input.toList.filter(p => p != ' ').filter(_.isDigit).map(p => p.toString.toInt) match {
          case row :: column :: Nil =>
            controller.handleClick(row, column)({ case Some(_) => })
            controller.getMillState({ case Some(state) => print(s"$state\n")})
            "valid command: " + input
          case _ =>
            "Wrong input: " + input
        }
    }
  }

  reactions += {
    case _ => printTui()
  }

  def printTui(): Unit = {
    controller.fieldToString({ case Some(field) => print(s"$field\n")})
    print(s"${GameState.state}\n")
  }
}
