package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Cell, Stone}
import de.htwg.se.mill.util.Command

class SetCommand(row:Int, col: Int, value:Cell, controller: Controller) extends Command {
  override def doStep: Unit = controller.field = controller.field.set(row, col, value)

  override def undoStep: Unit = controller.field = {
    controller.field = controller.field.replace(row, col, fieldBaseImpl.Cell(false, Stone("n")))
    controller.checkMill(row, col)
    controller.field
  }

  override def redoStep: Unit = {
    controller.field = controller.field.set(row, col, value)
    controller.checkMill(row, col)
  }
}
