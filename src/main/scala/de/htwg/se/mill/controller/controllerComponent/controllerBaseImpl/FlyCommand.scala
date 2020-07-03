package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Cell, Stone}
import de.htwg.se.mill.util.Command

class FlyCommand(rowOld:Int, colOld: Int, rowNew:Int, colNew:Int, controller: Controller) extends Command {
  override def doStep: Unit = controller.field = controller.field.fly(rowOld, colOld, rowNew, colNew)

  override def undoStep: Unit = controller.field = {
    controller.field = controller.field.replace(rowNew, colNew, Cell(false, Stone("n")))
    if (controller.roundCounter % 2 == 0) {
      controller.field = controller.field.replace(rowOld, rowOld, Cell(true, Stone("w+")))
    } else {
      controller.field = controller.field.replace(rowOld, rowOld, Cell(true, Stone("b+")))
    }
    controller.checkMill(rowNew, colNew)
    controller.field
  }

  override def redoStep: Unit = {
    controller.field = controller.field.fly(rowOld, colOld, rowNew, colNew)
    controller.checkMill(rowNew, colNew)
  }
}
