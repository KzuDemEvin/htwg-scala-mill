package de.htwg.se.mill.controller

import de.htwg.se.mill.model.{Cell, Color, Stone}
import de.htwg.se.mill.util.Command

class SetCommand(row:Int, col: Int, value:Cell, controller: Controller) extends Command {
  override def doStep: Unit =   controller.field = controller.field.set(row, col, value)

  override def undoStep: Unit = controller.field = controller.field.set(row, col, Cell(false, Stone(0,Color.noColor)))

  override def redoStep: Unit = controller.field = controller.field.set(row, col, value)

}
