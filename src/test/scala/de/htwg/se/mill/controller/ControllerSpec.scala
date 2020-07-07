package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "new" should {
      val field = new Field(7)
      val controllerUndoRedo = new Controller(field)
      "handle undo/redo correctly on an empty undo-stack" in {
        controllerUndoRedo.field.cell(0, 0).isSet should be(false)
        controllerUndoRedo.undo
        controllerUndoRedo.field.cell(0, 0).isSet should be(false)
        controllerUndoRedo.redo
        controllerUndoRedo.field.cell(0, 0).isSet should be(false)
      }
      "handle undo/redo of setting a cell correctly" in {
        controllerUndoRedo.set(0, 0)
        controllerUndoRedo.field.cell(0, 0).isSet should be(true)
        controllerUndoRedo.field.cell(0, 0).getContent.whichColor should be(Color.white)
        controllerUndoRedo.undo
        controllerUndoRedo.field.cell(0, 0).isSet should be(false)
        controllerUndoRedo.field.cell(0, 0).getContent.whichColor should be(Color.noColor)
        controllerUndoRedo.redo
        controllerUndoRedo.field.cell(0, 0).isSet should be(true)
        controllerUndoRedo.field.cell(0, 0).getContent.whichColor should be(Color.white)
        controllerUndoRedo.statusText should be("Redo")
      }
  }
    "ready to play" should {
      val field = new Field(7)
      val controller = new Controller(field)
      "be able to place random stones" in {
        controller.createRandomField(7)
        controller.placedStones() should be(18)
        controller.gameState should be("New field filled with random stones")
        controller.getRoundCounter should be(18)
      }
      "be able to reset its field" in {
        controller.createEmptyField(7)
        controller.placedStones() should be(0)
        controller.gameState should be("New field")
        controller.getRoundCounter should be(0)
      }
      "be able to return look in a string" in {
        controller.fieldToString should be(field.toString)
      }
      "return a correct roundCounter" in {
        controller.getRoundCounter should be(0)
      }
      "handle setting stones correctly" in {
        controller.set(0, 0)
        controller.cell(0, 0).isSet should be(true)
        controller.cell(0, 0).getContent.whichColor should be(Color.white)
        controller.set(0, 6)
        controller.cell(0, 6).isSet should be(true)
        controller.cell(0, 6).getContent.whichColor should be(Color.black)
        controller.placedStones() should be(2)
        controller.gameState should be("White's turn")
      }
      "handle white mills correctly" in {
        controller.set(3, 0)
        controller.set(3, 6)
        controller.set(6, 0)
        controller.checkMill(6, 0) should be("White Mill")
      }
      "handle removing stones correctly" in {
        controller.removeStone(6, 0) // not possible, same color + mill
        controller.cell(6, 0).isSet should be(true)
        controller.cell(6, 0).getContent.whichColor should be(Color.white)
        controller.placedStones() should be(5)
        controller.removeStone(3, 6)
        controller.cell(3, 6).isSet should be(false)
        controller.cell(3, 6).getContent.whichColor should be(Color.noColor)
        controller.placedStones() should be(4)
        controller.mgr.roundCounter should be(5)
      }
      "handle black mills correctly" in {
        controller.set(3, 6)
        controller.set(1, 1)
        controller.set(6, 6)
        controller.checkMill(6, 6) should be("Black Mill")
      }
      "not remove a stone when its in a mill" in {
        controller.removeStone(0, 6)
        controller.cell(0, 6).isSet should be(true)
        controller.removeStone(1, 1)
      }
      "handle move correctly" in {
        controller.set(1, 1) ; controller.set(1, 5) ; controller.set(3, 1) ; controller.set(3, 5) ; controller.set(4, 2)
        controller.set(5, 1) ; controller.set(5, 5) ; controller.set(2, 2) ; controller.set(2, 4)
        controller.moveStone(2, 4, 3, 4) // not possible yet <- set state
        controller.cell(2, 4).isSet should be(true)
        controller.cell(3, 4).isSet should be(false)
        controller.set(4, 4)
        controller.moveStone(3, 1, 3, 2) // possible
        controller.cell(3, 1).isSet should be(false)
        controller.cell(3, 2).isSet should be(true)
        controller.gameState should be("Black's turn")
        controller.mgr.roundCounter should be(19)
        controller.moveStone(6, 6, 3, 6) // not possible, same color
        controller.mgr.roundCounter should be(19) // should not change
        controller.moveStone(6, 6, 6, 3) // possible
        //controller.moveStone(6, 6, 6, 3) // not possible, not available
        controller.mgr.roundCounter should be(20)
        print(controller.fieldToString)
      }
      "change to fly mode" in {
        controller.moveStone(1, 1, 3, 1) // white
        controller.removeStone(2, 2)
        controller.moveStone(0, 6, 0, 3) // black
        controller.moveStone(3, 1, 1, 1) // white
        controller.moveStone(0, 3, 0, 6) // black

        controller.moveStone(1, 1, 3, 1) // white
        controller.removeStone(5, 1)
        controller.moveStone(0, 6, 0, 3) // black
        controller.moveStone(3, 1, 1, 1) // white
        controller.moveStone(0, 3, 0, 6) // black

        controller.moveStone(1, 1, 3, 1) // white
        controller.removeStone(6, 3)
        controller.moveStone(0, 6, 0, 3) // black
        controller.moveStone(3, 1, 1, 1) // white
        controller.moveStone(0, 3, 0, 6) // black

        controller.moveStone(1, 1, 3, 1) // white
        controller.removeStone(4, 4)
        controller.moveStone(0, 6, 0, 3) // black
        controller.moveStone(3, 1, 1, 1) // white
        controller.moveStone(0, 3, 0, 6) // black

        controller.moveStone(1, 1, 3, 1) // white
        controller.removeStone(3, 5)
        controller.mgr.player2.mode should be("FlyMode")
        printf(controller.placedBlackStones().toString)
      }


//      "should return valid values with its methods" in {
//        controller.cell(0, 0).getContent.whichColor should be (Color.white)
//        controller.isSet(0, 0) should be (true)
//        controller.isSet(0,3) should be (false)
//        controller.available(6, 6) should be (true)
//        controller.possiblePosition(0, 1) should be(false)
//        controller.placedStones() should be (1)
//        controller.fieldsize should be (7)
//      }
    }


//    "move a stone" in {
//      controller.set(6,6)
//      controller.moveStone(6,6,6,3)
//
//    }
//    "should handle a no mill correct" in {
//      controller.set(2,2)
//      controller.millText should be("No Mill")
//    }
//    "should handle a white mill correct" in {
//      controller.set(2,4)
//      controller.set(3,2)
//      controller.set(3,4)
//      controller.set(4,2)
//      controller.millText should be("White Mill")
//    }
//    "should handle a black mill correct" in {
//      controller.set(4,4)
//      controller.millText should be("Black Mill")
//    }
  }
}
