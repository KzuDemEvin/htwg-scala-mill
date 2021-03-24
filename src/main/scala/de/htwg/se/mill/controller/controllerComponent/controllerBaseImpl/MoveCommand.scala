package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

class MoveCommand(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int, controller: Controller) extends Command(rowOld, colOld, rowNew, colNew, controller) {
  override def doStep: Unit = controller.field = controller.field.moveStone(rowOld, colOld, rowNew, colNew)

  override def redoStep: Unit = {
    controller.field = controller.field.moveStone(rowOld, colOld, rowNew, colNew)
    controller.checkMill(rowNew, colNew)
  }
}