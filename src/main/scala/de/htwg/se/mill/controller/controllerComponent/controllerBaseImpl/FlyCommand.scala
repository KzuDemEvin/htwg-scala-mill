package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.util.Command

class FlyCommand(rowOld:Int, colOld: Int, rowNew:Int, colNew:Int, controller: Controller) extends Command {
  override def doStep: Unit = controller.field = controller.field.fly(rowOld, colOld, rowNew, colNew)

  override def undoStep: Unit = controller.field = {
    controller.field = controller.field.replace(rowNew, colNew, Cell("ce"))
    if (controller.mgr.blackTurn()) {
      controller.field = controller.field.replace(rowOld, rowOld, Cell("cw"))
    } else {
      controller.field = controller.field.replace(rowOld, rowOld, Cell("cb"))
    }
    controller.checkMill(rowNew, colNew)
    controller.field
  }

  override def redoStep: Unit = {
    controller.field = controller.field.fly(rowOld, colOld, rowNew, colNew)
    controller.checkMill(rowNew, colNew)
  }
}
