package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.{Controller, SetCommand}
import de.htwg.se.mill.model.fieldComponent.{Cell, Color}
import de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl.Field
import org.scalatest.{Matchers, WordSpec}

class SetCommandSpec extends WordSpec with Matchers {
  "A MoveCommand" when {
    "new" should {
      val normalSize = 7
      val field = new Field(normalSize)
      val controller = new Controller(field)
      val command = new SetCommand(0, 0, Cell("cw"), controller)
      "Be able to move a white stone" in {
        command.doStep
        controller.cell(0, 0).content.color should be (Color.white)
      }
      "Be able to undo a step" in {
        command.undoStep
        controller.cell(0, 0).content.color should be (Color.noColor)
      }
      "Be able to redo a step" in {
        command.redoStep
        controller.cell(0, 0).content.color should be (Color.white)
      }
    }
  }
}
