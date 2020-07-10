package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.{Controller, MoveCommand}
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class MoveCommandSpec extends WordSpec with Matchers {
  "A MoveCommand" when {
    "new" should {
      val field = new Field(7)
      val controller = new Controller(field)
      val command = new MoveCommand(0, 0, 0, 3, controller)
      "Be able to move a white stone" in {
        controller.handleClick(0, 0) // placing a white stone
        command.doStep
        controller.cell(0, 3).getContent.whichColor should be (Color.white)
      }
      "Be able to undo a step" in {
        command.undoStep
        controller.cell(0, 3).getContent.whichColor should be (Color.noColor)
        controller.cell(0, 0).getContent.whichColor should be (Color.white)
      }
      "Be able to redo a step" in {
        command.redoStep
        controller.cell(0, 3).getContent.whichColor should be (Color.white)
        controller.cell(0, 0).getContent.whichColor should be (Color.noColor)
      }
    }
  }
}
