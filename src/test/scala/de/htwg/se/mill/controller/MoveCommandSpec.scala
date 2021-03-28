package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.{Controller, MoveCommand}
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class MoveCommandSpec extends WordSpec with Matchers {
  "A MoveCommand" when {
    "new can move white stones" should {
      val field = new Field(7)
      val controller = new Controller(field)
      val command = new MoveCommand(0, 0, 0, 3, controller)
      "Be able to move a white stone" in {
        controller.handleClick(0, 0) // placing a white stone
        command.doStep
        controller.cell(0, 3).content.color should be (Color.white)
      }
      "Be able to undo a white step" in {
        command.undoStep
        controller.cell(0, 3).content.color should be (Color.noColor)
        controller.cell(0, 0).content.color should be (Color.white)
      }
      "Be able to redo a step" in {
        command.redoStep
        controller.cell(0, 3).content.color should be (Color.white)
        controller.cell(0, 0).content.color should be (Color.noColor)
      }
    }
    "new can move black stones" should {
      val field = new Field(7)
      val controller = new Controller(field)
      val commandwhite = new MoveCommand(0, 0, 0, 3, controller)
      controller.handleClick(0, 0) // placing a white stone
      commandwhite.doStep
      val commandblack = new MoveCommand(6, 6, 6, 3, controller)
      "Be able to move a black stone" in {
        controller.handleClick(6, 6) // placing a black stone
        commandblack.doStep
        controller.cell(6, 3).content.color should be (Color.black)
      }
      "Be able to undo a black step" in {
        commandblack.undoStep
        controller.cell(6, 3).content.color should be (Color.noColor)
        controller.cell(6, 6).content.color should be (Color.black)
      }
      "Be able to redo a step" in {
        commandblack.redoStep
        controller.cell(6, 3).content.color should be (Color.black)
        controller.cell(6, 6).content.color should be (Color.noColor)
      }
    }
  }
}
