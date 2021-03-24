package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.util.CommandTrait

class SetCommand(row: Int, col: Int, value: Cell, controller: Controller) extends CommandTrait {
  override def doStep: Unit = controller.field = controller.field.set(row, col, value)

  override def undoStep: Unit = controller.field = {
    controller.field = controller.field.replace(row, col, Cell("ce"))
    controller.checkMill(row, col)
    controller.field
  }

  override def redoStep: Unit = {
    controller.field = controller.field.set(row, col, value)
    controller.checkMill(row, col)
  }
}
