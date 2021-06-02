package de.htwg.se.mill.controller.controllerRoundManager

import com.google.gson.Gson
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.reflectiveCalls
import scala.util.{Failure, Success}

class RoundManagerControllerSpec extends AnyWordSpec with Matchers {
  val normalSize = 7
  "A Controller" when {
    "new" should {
      val field = new Field(normalSize)
      "handle undo/redo correctly on an empty undo-stack" in {
        val controllerUndoRedo = new RoundManagerController(field)
        controllerUndoRedo.cell(0, 0).isSet should be(false)
        controllerUndoRedo.undo()
        controllerUndoRedo.cell(0, 0).isSet should be(false)
        controllerUndoRedo.mgr.roundCounter should be(0)
        controllerUndoRedo.redo()
        controllerUndoRedo.field.cell(0, 0).isSet should be(false)
        controllerUndoRedo.mgr.roundCounter should be(0)
      }
      "handle undo/redo of setting a cell correctly" in {
        val controllerUndoRedo = new RoundManagerController(field)
        controllerUndoRedo.handleClick(0, 0)
        controllerUndoRedo.cell(0, 0).isSet should be(true)
        controllerUndoRedo.cell(0, 0).content.color should be(Color.white)
        controllerUndoRedo.undo()
        controllerUndoRedo.cell(0, 0).isSet should be(false)
        controllerUndoRedo.cell(0, 0).content.color should be(Color.noColor)
        controllerUndoRedo.redo()
        controllerUndoRedo.cell(0, 0).isSet should be(true)
        controllerUndoRedo.cell(0, 0).content.color should be(Color.white)
      }
    }
    "ready to play" should {
      val field = new Field(normalSize)
      val controller = new RoundManagerController(field)
      "return valid values with its methods" in {
        controller.cell(0, 0).content.color should be(Color.noColor)
        controller.mgr.field.available(6, 6) should be(true)
        controller.possiblePosition(0, 1) should be(new Gson().toJson(false))
        controller.mgr.field.placedStones() should be(0)
        controller.mgr.field.placedWhiteStones() should be(0)
        controller.mgr.field.size should be(normalSize)
        controller.turn() should be(new Gson().toJson("White"))
        controller.roundCounter() should be(new Gson().toJson(0))
      }
      "be able to place random stones" in {
        controller.createRandomField(normalSize)
        controller.mgr.field.size should be(normalSize)
        controller.mgr.field.placedStones() should be(18)
        controller.mgr.roundCounter should be(18)
      }
      "be able to reset its field" in {
        controller.createEmptyField(7)
        controller.mgr.field.placedStones() should be(0)
        controller.mgr.roundCounter should be(0)
      }
      "be able to return look in a string" in {
        controller.mgr.field.toString should be(field.toString)
      }
      "return a correct roundCounter" in {
        controller.mgr.roundCounter should be(0)
      }
      "handle setting stones correctly" in {
        controller.handleClick(0, 0)
        controller.cell(0, 0).isSet should be(true)
        controller.cell(0, 0).content.color should be(Color.white)
        controller.handleClick(0, 6)
        controller.cell(0, 6).isSet should be(true)
        controller.cell(0, 6).content.color should be(Color.black)
        controller.mgr.field.placedStones() should be(2)
      }
      "handle white mills correctly" in {
        controller.handleClick(3, 0)
        controller.handleClick(3, 6)
        controller.handleClick(6, 0)
        controller.mgr.field.millState should be("White Mill")
      }
      "handle removing stones correctly" in {
        controller.handleClick(6, 0) // remove, not possible, same color + mill
        controller.cell(6, 0).isSet should be(true)
        controller.cell(6, 0).content.color should be(Color.white)
        controller.mgr.field.placedStones() should be(5)
        controller.handleClick(3, 6) // remove, possible
        controller.cell(3, 6).isSet should be(false)
        controller.cell(3, 6).content.color should be(Color.noColor)
        controller.mgr.field.placedStones() should be(4)
        controller.mgr.roundCounter should be(5)
      }
      "handle black mills correctly" in {
        controller.handleClick(3, 6) // set
        controller.handleClick(1, 1) // set
        controller.handleClick(6, 6) // set
        controller.mgr.field.millState should be("Black Mill")
      }
      "not remove a stone when its in a mill" in {
        controller.handleClick(0, 6) // remove, not possible
        controller.cell(0, 6).isSet should be(true)
        println(controller.mgr.field.toString)
        controller.handleClick(1, 1) // remove
      }
      "handle move correctly" in {
        controller.handleClick(1, 1) ; controller.handleClick(1, 5) // set
        controller.handleClick(3, 1) ; controller.handleClick(3, 5) // set
        controller.handleClick(4, 2) ; controller.handleClick(5, 1) // set
        controller.handleClick(5, 5) ; controller.handleClick(2, 2) // set
        controller.handleClick(2, 4) ; controller.handleClick(4, 4) // set
        controller.handleClick(3, 1) ; controller.handleClick(3, 2) // move, possible
        controller.cell(3, 1).isSet should be(false)
        controller.cell(3, 2).isSet should be(true)
        controller.mgr.roundCounter should be(19)
        controller.handleClick(6, 6) ; controller.handleClick(6, 3) // move, possible
        controller.mgr.roundCounter should be(20)
      }
      "change to fly mode" in {
        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(2, 2) // remove
        controller.handleClick(0, 6) ; controller.handleClick(0, 3) // black
        controller.handleClick(3, 1) ; controller.handleClick(1, 1) // white
        controller.handleClick(0, 3) ; controller.handleClick(0, 6) // black

        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(5, 1) // remove
        controller.handleClick(0, 6) ; controller.handleClick(0, 3) // black
        controller.handleClick(3, 1) ; controller.handleClick(1, 1) // white
        controller.handleClick(0, 3) ; controller.handleClick(0, 6) // black

        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(6, 3) // remove
        controller.handleClick(0, 6) ; controller.handleClick(0, 3) // black
        controller.handleClick(3, 1) ; controller.handleClick(1, 1) // white
        controller.handleClick(0, 3) ; controller.handleClick(0, 6) // black

        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(4, 4) // remove
        controller.handleClick(0, 6) ; controller.handleClick(0, 3) // black
        controller.handleClick(3, 1) ; controller.handleClick(1, 1) // white
        controller.handleClick(0, 3) ; controller.handleClick(0, 6) // black

        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(3, 5) // remove
        controller.mgr.player2Mode.handle should be("FlyMode")
        controller.mgr.field.placedBlackStones() should be(3)
        printf(controller.mgr.field.toString)
      }
      "handle fly correctly" in {
        controller.handleClick(1, 5) ; controller.handleClick(6, 6)
        controller.cell(1, 5).isSet should be(false)
        controller.cell(6, 6).isSet should be (true)
      }
      "handle remove in flymode correctly" in {
        controller.handleClick(4, 2)
        controller.cell(4, 2).isSet should be(false)
      }
      "handle choosing a winner correctly" in {
        controller.mgr.winner should be(0) // no Winner
        controller.handleClick(3, 1) ; controller.handleClick(1, 1) // white
        controller.handleClick(0, 6) ; controller.handleClick(5, 1) // black
        controller.handleClick(1, 1) ; controller.handleClick(3, 1) // white
        controller.handleClick(5, 1) // remove
        controller.mgr.winner should be(2) // white winner
      }
      "check if at the end a player is choosing a stone in a mill to remove and to win" should {
        "black wins" in {
          val field = new Field(7)
          val controller = new RoundManagerController(field)
          controller.handleClick(0, 0) // w
          controller.fieldAsString() should be("Mill Gameboard:\n w  -  -  o  -  -  o \n -  o  -  o  -  o  - " +
            "\n -  -  o  o  o  -  - \n o  o  o  -  o  o  o \n -  -  o  o  o  -  - \n -  o  -  o  -  o  - \n o" +
            "  -  -  o  -  -  o \n")
          controller.handleClick(6, 0) // b
          controller.handleClick(0, 3) // w
          controller.handleClick(6, 3) // b
          controller.handleClick(0, 6) // w
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(6, 3) //Remove
          controller.handleClick(6, 3)
          controller.handleClick(1, 1)
          controller.handleClick(6, 6)
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(1, 1) //Remove
          controller.handleClick(1, 1) // w
          controller.handleClick(5, 1) // b
          controller.handleClick(1, 3) // w
          controller.handleClick(5, 3) // b
          controller.handleClick(1, 5) // w
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(5, 3) //remove
          controller.handleClick(5, 3)
          controller.handleClick(2, 2)
          controller.handleClick(5, 5)
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(2, 2) //remove
          controller.handleClick(2, 2) // w
          controller.handleClick(4, 2) // b
          controller.mgr.roundCounter should be (18)
          controller.handleClick(2, 2); controller.handleClick(2, 3)
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(4, 2) // remove
          controller.mgr.roundCounter should be (19)
          controller.handleClick(5, 3); controller.handleClick(4, 3) // b
          controller.mgr.roundCounter should be (20)
          controller.roundCounter() should be(new Gson().toJson(20))
          controller.turn() should be(new Gson().toJson("White"))
          controller.handleClick(2, 3); controller.handleClick(2, 2) // w
          controller.mgr.roundCounter should be (21)
          controller.handleClick(4, 3); controller.handleClick(5, 3) // b
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(2, 2) //remove
          controller.mgr.roundCounter should be (22)
          controller.handleClick(1, 3); controller.handleClick(2, 3) // w
          controller.turn() should be(new Gson().toJson("Black"))
          controller.mgr.roundCounter should be (23)
          controller.handleClick(5, 3); controller.handleClick(4, 3) // b
          controller.mgr.roundCounter should be (24)
          controller.handleClick(2, 3); controller.handleClick(2, 2) // w
          controller.mgr.roundCounter should be (25)
          controller.handleClick(6, 3); controller.handleClick(5, 3) // b
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(2, 2) //remove
          controller.mgr.roundCounter should be (26)
          controller.handleClick(0, 3); controller.handleClick(1, 3) // w
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(4, 3) //remove
          controller.mgr.roundCounter should be (27)
          controller.handleClick(5, 3); controller.handleClick(6, 3) // b
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(0, 0) //remove
          controller.mgr.roundCounter should be (28)
          controller.handleClick(1, 3); controller.handleClick(0, 3) // w
          controller.mgr.roundCounter should be (29)
          controller.roundCounter() should be(new Gson().toJson(29))
          controller.handleClick(6, 3); controller.handleClick(5, 3) // b
          controller.mgr.roundCounter should be (29)
          controller.mgr.field.millState should be ("Black Mill")
          controller.handleClick(0, 6) //remove
          controller.mgr.roundCounter should be (30)
          controller.handleClick(0, 3); controller.handleClick(1, 3) // w
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(6, 6) //remove
          controller.mgr.roundCounter should be (31)
          controller.mgr.field.millState should be ("No Mill")
          controller.handleClick(6, 0); controller.handleClick(3, 0) // b

          controller.handleClick(1, 3); controller.handleClick(0, 3) // w
          controller.handleClick(5, 3); controller.handleClick(6, 3) // b

          controller.handleClick(0, 3); controller.handleClick(1, 3) // w
          controller.mgr.field.millState should be ("White Mill")
          controller.handleClick(3, 0) //remove
          controller.mgr.field.millState should be ("No Mill")
          controller.handleClick(6, 3); controller.handleClick(5, 3) // b
          controller.handleClick(1, 1) //remove
          controller.mgr.winner should be(1)
          controller.winner() should be(new Gson().toJson(1))
          controller.winnerText().onComplete({
            case Success(winnerText) => winnerText should be(new Gson().toJson("Player 2 wins! (black)"))
            case Failure(e) => e
          })
        }
        "white wins" in {
          val field = new Field(7)
          val controller = new RoundManagerController(field)
          controller.handleClick(0, 0)
          controller.handleClick(6, 0)
          controller.handleClick(0, 3)
          controller.handleClick(6, 3)
          controller.handleClick(0, 6)
          controller.handleClick(6, 3) //Remove
          controller.handleClick(6, 3)
          controller.handleClick(1, 1)
          controller.handleClick(6, 6)
          controller.handleClick(1, 1) //Remove
          controller.handleClick(1, 1)
          controller.handleClick(5, 1)
          controller.handleClick(1, 3)
          controller.handleClick(5, 3)
          controller.handleClick(1, 5)
          controller.handleClick(5, 3) //remove
          controller.handleClick(5, 3)
          controller.handleClick(2, 2)
          controller.handleClick(5, 5)
          controller.handleClick(2, 2) //remove
          controller.handleClick(2, 2)
          controller.handleClick(4, 2)
          controller.handleClick(2, 2); controller.handleClick(2, 3)
          controller.handleClick(4, 2)
          controller.handleClick(5, 3); controller.handleClick(4, 3)
          controller.handleClick(2, 3); controller.handleClick(2, 2)
          controller.handleClick(4, 3); controller.handleClick(5, 3)
          controller.handleClick(2, 2) //remove
          controller.handleClick(1, 3); controller.handleClick(2, 3)
          controller.handleClick(5, 3); controller.handleClick(4, 3)
          controller.handleClick(2, 3); controller.handleClick(2, 2)
          controller.handleClick(6, 3); controller.handleClick(5, 3)
          controller.handleClick(2, 2) //remove
          controller.handleClick(0, 3); controller.handleClick(1, 3)
          controller.handleClick(4, 3) //remove
          controller.handleClick(5, 3); controller.handleClick(6, 3)
          controller.handleClick(0, 0) //remove
          controller.handleClick(1, 3); controller.handleClick(0, 3)
          controller.handleClick(6, 0); controller.handleClick(3, 0)
          controller.handleClick(0, 3); controller.handleClick(1, 3)
          controller.handleClick(3, 0) //remove
          controller.handleClick(6, 6); controller.handleClick(3, 6)
          controller.handleClick(1, 3); controller.handleClick(0, 3)
          controller.handleClick(3, 6); controller.handleClick(6, 6)
          controller.handleClick(0, 3); controller.handleClick(1, 3)
          controller.handleClick(6, 6) //remove
          controller.handleClick(6, 3); controller.handleClick(6, 6)
          controller.handleClick(0, 6); controller.handleClick(3, 6)
          controller.handleClick(6, 6); controller.handleClick(6, 3)
          controller.handleClick(1, 3); controller.handleClick(0, 3)
          controller.handleClick(6, 3); controller.handleClick(5, 3)
          controller.handleClick(3, 6) //remove
          controller.handleClick(0, 3); controller.handleClick(1, 3)
          controller.handleClick(0, 0) //remove
          controller.mgr.winner should be(0)
          controller.handleClick(5, 3) //remove
          controller.mgr.winner should be(2)
          controller.winner() should be(new Gson().toJson(2))
          controller.winnerText().onComplete({
            case Success(winnerText) => winnerText should be(new Gson().toJson("Player 1 wins! (white)"))
            case Failure(e) => e
          })
        }
      }
    }

  }
}
