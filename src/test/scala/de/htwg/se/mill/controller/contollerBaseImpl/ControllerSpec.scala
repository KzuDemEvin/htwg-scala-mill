package de.htwg.se.mill.controller.contollerBaseImpl

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.language.reflectiveCalls

class ControllerSpec extends AnyWordSpec with Matchers {
  val normalSize = 7
  "A Controller" when {
    "new" should {
      val controller = new Controller
       "be able to undo and redo" in {
         controller.undo()
         controller.gameState should be("Undo")
         controller.redo()
         controller.gameState should be("Redo")
      }
      "be able to save and load a field" in {
        controller.save()
        controller.gameState should be("Game saved")
        controller.load()
        controller.gameState should be("Game loaded")
      }
    }
    "ready to play" should {
      val controller = new Controller
      "return valid values with its methods" in {
        controller.color(0, 0)({ case Some(c) => c should be("noColor") })
        controller.isSet(0, 0)({ case Some(isSet) => isSet.toBoolean should be(false) })
        controller.possiblePosition(0, 1) should be(false)
        controller.fieldsize should be(normalSize)
      }
      "be able to place random stones" in {
        controller.createRandomFieldSync(normalSize)
        controller.fieldsize should be(normalSize)
        controller.gameState should be("New field filled with random stones")
        controller.getRoundCounter({ case Some(rc) => rc should be(18) })
      }
      "be able to reset its field" in {
        controller.createEmptyFieldSync(normalSize)
        controller.gameState should be("New field")
        controller.getRoundCounter({ case Some(rc) => rc should be(0) })
      }
      "return a correct roundCounter" in {
        controller.getRoundCounter({ case Some(rc) => rc should be(0) })
      }
      "handle setting stones correctly" in {
        controller.createEmptyFieldSync(normalSize)
        controller.handleClickSync(0, 0)
        controller.isSet(0, 0)({ case Some(isSet) => isSet.toBoolean should be(true) })
        controller.handleClick(0, 6)({ case Some(_) => controller.gameState should be("Black's turn")})
        controller.isSet(0, 6)({ case Some(isSet) => isSet.toBoolean should be(true) })
        controller.color(0, 6)({ case Some(color) => color should be("black") })
      }
      "handle white mills correctly" in {
        controller.createEmptyField(normalSize)
        controller.handleClickSync(0, 0) // w
        controller.handleClickSync(0, 6) // b
        controller.handleClickSync(3, 0) // w
        controller.handleClickSync(3, 6) // b
        controller.handleClickSync(6, 0) // w
        controller.getMillState({ case Some(millState) => millState should be("White Mill") })
      }
      "handle removing stones correctly" in {
        controller.createEmptyField(normalSize)
        controller.handleClickSync(0, 0) // w
        controller.handleClickSync(0, 6) // b
        controller.handleClickSync(3, 0) // w
        controller.handleClickSync(3, 6) // b
        controller.handleClickSync(6, 0) // w
        controller.handleClickSync(6, 0) // remove, not possible, same color + mill
        controller.isSet(6, 0)({ case Some(isSet) => isSet.toBoolean should be(true) })
        controller.color(6, 0)({ case Some(color) => color should be("black") })
        controller.handleClickSync(3, 6) // remove, possible
        controller.isSet(3, 6)({ case Some(isSet) => isSet.toBoolean should be(false) })
        controller.color(3, 6)({ case Some(color) => color should be("noColor") })
        controller.getRoundCounter({ case Some(rc) => rc.toInt should be(3) })
      }
      "handle black mills correctly" in {
        controller.createEmptyField(normalSize)
        controller.handleClickSync(0, 6) // w
        controller.handleClickSync(0, 0) // b
        controller.handleClickSync(3, 6) // w
        controller.handleClickSync(6, 0) // b
        controller.handleClickSync(1, 1) // w
        controller.handleClickSync(3, 0) // b
        controller.getMillState({ case Some(millState) => millState should be("Black Mill") })
      }
      "check if at the end a player is choosing a stone in a mill to remove and to win" should {
        "black wins" in {
          val controller = new Controller
          controller.handleClickSync(0, 0)
          controller.handleClickSync(6, 0)
          controller.handleClickSync(0, 3)
          controller.handleClickSync(6, 3)
          controller.handleClickSync(0, 6)
          controller.handleClickSync(6, 3) //Remove
          controller.handleClickSync(6, 3)
          controller.handleClickSync(1, 1)
          controller.handleClickSync(6, 6)
          controller.handleClickSync(1, 1) //Remove
          controller.handleClickSync(1, 1)
          controller.handleClickSync(5, 1)
          controller.handleClickSync(1, 3)
          controller.handleClickSync(5, 3)
          controller.handleClickSync(1, 5)
          controller.handleClickSync(5, 3) //remove
          controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2)
          controller.handleClickSync(5, 5)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(2, 2)
          controller.handleClickSync(4, 2)
          controller.handleClickSync(2, 2);
          controller.handleClickSync(2, 3)
          controller.handleClickSync(4, 2)
          controller.handleClickSync(5, 3);
          controller.handleClickSync(4, 3)
          controller.handleClickSync(2, 3);
          controller.handleClickSync(2, 2)
          controller.handleClickSync(4, 3);
          controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(1, 3);
          controller.handleClickSync(2, 3)
          controller.handleClickSync(5, 3);
          controller.handleClickSync(4, 3)
          controller.handleClickSync(2, 3);
          controller.handleClickSync(2, 2)
          controller.handleClickSync(6, 3);
          controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(0, 3);
          controller.handleClickSync(1, 3)
          controller.handleClickSync(4, 3) //remove
          controller.handleClickSync(5, 3);
          controller.handleClickSync(6, 3)
          controller.handleClickSync(0, 0) //remove
          controller.handleClickSync(1, 3);
          controller.handleClickSync(0, 3)
          controller.handleClickSync(6, 3);
          controller.handleClickSync(5, 3)
          controller.handleClickSync(0, 6) //remove
          controller.handleClickSync(0, 3);
          controller.handleClickSync(1, 3)
          controller.handleClickSync(6, 6) //remove
          controller.handleClickSync(6, 0);
          controller.handleClickSync(3, 0)

          controller.handleClickSync(1, 3);
          controller.handleClickSync(0, 3)
          controller.handleClickSync(5, 3);
          controller.handleClickSync(6, 3)

          controller.handleClickSync(0, 3);
          controller.handleClickSync(1, 3)
          controller.handleClickSync(3, 0) //remove
          controller.handleClickSync(6, 3);
          controller.handleClickSync(5, 3)
          controller.handleClickSync(1, 1) //remove
          controller.getWinner({ case Some(winner) => winner.toInt should be(2) })
        }
        "white wins" in {
          val controller = new Controller
          controller.handleClickSync(0, 0)
          controller.handleClickSync(6, 0)
          controller.handleClickSync(0, 3)
          controller.handleClickSync(6, 3)
          controller.handleClickSync(0, 6)
          controller.handleClickSync(6, 3) //Remove
          controller.handleClickSync(6, 3)
          controller.handleClickSync(1, 1)
          controller.handleClickSync(6, 6)
          controller.handleClickSync(1, 1) //Remove
          controller.handleClickSync(1, 1)
          controller.handleClickSync(5, 1)
          controller.handleClickSync(1, 3)
          controller.handleClickSync(5, 3)
          controller.handleClickSync(1, 5)
          controller.handleClickSync(5, 3) //remove
          controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2)
          controller.handleClickSync(5, 5)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(2, 2)
          controller.handleClickSync(4, 2)
          controller.handleClickSync(2, 2);controller.handleClickSync(2, 3)
          controller.handleClickSync(4, 2)
          controller.handleClickSync(5, 3);
          controller.handleClickSync(4, 3)
          controller.handleClickSync(2, 3);
          controller.handleClickSync(2, 2)
          controller.handleClickSync(4, 3);
          controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(1, 3);controller.handleClickSync(2, 3)
          controller.handleClickSync(5, 3);
          controller.handleClickSync(4, 3)
          controller.handleClickSync(2, 3);controller.handleClickSync(2, 2)
          controller.handleClickSync(6, 3);controller.handleClickSync(5, 3)
          controller.handleClickSync(2, 2) //remove
          controller.handleClickSync(0, 3);controller.handleClickSync(1, 3)
          controller.handleClickSync(4, 3) //remove
          controller.handleClickSync(5, 3);controller.handleClickSync(6, 3)
          controller.handleClickSync(0, 0) //remove
          controller.handleClickSync(1, 3);controller.handleClickSync(0, 3)
          controller.handleClickSync(6, 0);controller.handleClickSync(3, 0)
          controller.handleClickSync(0, 3);controller.handleClickSync(1, 3)
          controller.handleClickSync(3, 0) //remove
          controller.handleClickSync(6, 6);controller.handleClickSync(3, 6)
          controller.handleClickSync(1, 3);controller.handleClickSync(0, 3)
          controller.handleClickSync(3, 6);controller.handleClickSync(6, 6)
          controller.handleClickSync(0, 3);controller.handleClickSync(1, 3)
          controller.handleClickSync(6, 6) //remove
          controller.handleClickSync(6, 3);controller.handleClickSync(6, 6)
          controller.handleClickSync(0, 6);controller.handleClickSync(3, 6)
          controller.handleClickSync(6, 6);controller.handleClickSync(6, 3)
          controller.handleClickSync(1, 3);controller.handleClickSync(0, 3)
          controller.handleClickSync(6, 3);controller.handleClickSync(5, 3)
          controller.handleClickSync(3, 6) //remove
          controller.handleClickSync(0, 3);controller.handleClickSync(1, 3)
          controller.handleClickSync(0, 0) //remove
          controller.getWinner({ case Some(winner) => winner.toInt should be(0) })
          controller.handleClickSync(5, 3) //remove
          controller.getWinner({ case Some(winner) => winner.toInt should be(1) })
        }
      }
    }
  }
}
