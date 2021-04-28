package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.{Controller, FlyCommand, MoveCommand}
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class FlyCommandSpec extends WordSpec with Matchers {
  "A Fly Command" when {
    "new can fly a white stone" should {
      val field = new Field(7)
      val controller = new Controller(field)
      val command = new FlyCommand(0, 0, 6, 6, controller)
      "Be able to fly a white stone" in {
        controller.handleClick(0, 0) // placing a white stone
        command.doStep
        controller.cell(6, 6).content.color should be (Color.white)
      }
      "Be able to undo a step" in {
        command.undoStep
        controller.cell(6, 6).content.color should be (Color.noColor)
        controller.cell(0, 0).content.color should be (Color.white)
      }
      "Be able to redo a step" in {
        command.redoStep
        controller.cell(6, 6).content.color should be (Color.white)
        controller.cell(0, 0).content.color should be (Color.noColor)
      }
    }
    "new can move black stones" should {
      val field = new Field(7)
      val controller = new Controller(field)
      val commandwhite = new FlyCommand(0, 0, 0, 6, controller)
      controller.handleClick(0, 0) // placing a white stone
      commandwhite.doStep
      val commandblack = new FlyCommand(1, 1, 6, 0, controller)
      "Be able to fly a black stone" in {
        controller.handleClick(1, 1) // placing a black stone
        commandblack.doStep
        controller.cell(6, 0).content.color should be (Color.black)
      }
      "Be able to undo a black step" in {
        commandblack.undoStep
        controller.cell(6, 0).content.color should be (Color.noColor)
        controller.cell(1, 1).content.color should be (Color.black)
      }
      "Be able to redo a step" in {
        commandblack.redoStep
        controller.cell(6, 0).content.color should be (Color.black)
        controller.cell(1, 1).content.color should be (Color.noColor)
      }
    }
  }
}
