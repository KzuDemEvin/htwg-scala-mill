package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.util.CommandTrait

abstract class Command(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int, controller: Controller) extends CommandTrait {
  override def undoStep: Unit = controller.field = {
    controller.field = controller.field
      .replace(rowNew, colNew, Cell("ce"))
      .replace(rowOld, rowOld, if (controller.mgr.blackTurn()) {
        Cell("cw")
      } else {
        Cell("cb")
      })
    controller.checkMill(rowNew, colNew)
    controller.field
  }

}
