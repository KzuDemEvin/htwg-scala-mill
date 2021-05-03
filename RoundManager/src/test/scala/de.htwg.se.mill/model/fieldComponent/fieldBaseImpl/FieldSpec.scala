package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import de.htwg.se.mill.model.fieldComponent._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec with Matchers {
  val weirdSize = 2
  val tinySize = 3
  val smallSize = 5
  val normalSize = 7
  "A Field is the field of Mill. A Field" when {
    "can be constructed in different sizes" in {
      new Field(tinySize).size should be(tinySize)
      new Field(smallSize).size should be(smallSize)
      new Field(weirdSize).size should be(weirdSize)
    }
    "to be constructed in a normal size" should {
      val normalField = new Field(normalSize)
      "have the size 7" in {
        normalField.size should be(normalSize)
      }
      "give access to its cells" in {
        new Field(normalSize).cell(0, 0).content.color should be(Color.noColor)
      }
      "can check if the given position is valid" in {
        new Field(normalSize).possiblePosition(0, 1) should be(false)
      }
      "can check if the given position is available" in {
        new Field(normalSize).available(0, 0) should be(true)
      }
      "allow to set individual Cells and remain immutable" in {
        val changedField = new Field(normalSize).set(0, 0, Cell("cw"))._1
        changedField.cell(0, 0).content.color should be(Color.white)
        normalField.cell(0, 0).content.color should be(Color.noColor)
      }
      "should return a string which looks like the gameboard" in {
        var blackwhiteField = new Field(normalSize).set(0, 0, Cell("cw"))._1
        blackwhiteField = blackwhiteField.set(1, 1, Cell("cb"))._1
        blackwhiteField.toString should be("Mill Gameboard:\n w  -  -  o  -  -  o \n -  b  -  o  -  o  - " +
          "\n -  -  o  o  o  -  - \n o  o  o  -  o  o  o \n -  -  o  o  o  -  - \n -  o  -  o  -  o  - \n o" +
          "  -  -  o  -  -  o \n")
        blackwhiteField = blackwhiteField.set(1, 1, Cell("cw"))._1
        blackwhiteField.cell(1, 1).content.color should be(Color.black)
      }
      "should be able to set and return a roundCounter" in {
        val roundCounter = 3
        new Field(normalSize).setRoundCounter(roundCounter).savedRoundCounter should be(roundCounter)
      }
      "should be able to set and return player 1 in setMode" in {
        new Field(normalSize).setPlayer1Mode("SetMode").player1Mode should be("SetMode")
      }
      "should be able to set and return player 1 in moveMode" in {
        new Field(normalSize).setPlayer1Mode("MoveMode").player1Mode should be("MoveMode")
      }
      "should be able to set and return player 1 name" in {
        new Field(normalSize).setPlayer1Name("Name1").player1Name should be("Name1")
      }
      "should be able to set and return player 2 flyMode" in {
        new Field(normalSize).setPlayer2Mode("FlyMode").player2Mode should be("FlyMode")
      }
      "should be able to set and return player 2 name" in {
        new Field(normalSize).setPlayer2Name("Name2").player2Name should be("Name2")
      }
      "should be able to move a stone to a new position" in {
        val row = 0
        val col = 0
        val rowNew = 0
        val colNew = 3
        var changedfield = new Field(normalSize).set(row, col, Cell("cw"))._1
        changedfield = changedfield.moveStone(row, col, rowNew, colNew)._1
        changedfield.cell(rowNew, colNew).content.color should be(Color.white)
      }
      "should not move a stone to a new position" in {
        val field = new Field(normalSize).set(0, 0, Cell("cw"))._1
        val notMovedStoneField: FieldInterface = field.moveStone(0, 0, 2, 3)._1
        notMovedStoneField should be(field)
      }
      "should be able to fly with a stone to a new position" in {
        val changedfield = normalField.set(0, 0, Cell("cw"))._1
        changedfield.fly(0, 0, 0, 3)._1.cell(0, 3).content.color should be(Color.white)
      }
      "should be able to remove a stone, for example when there is a mill" should {
        var changedfield: FieldInterface = new Field(normalSize).set(0, 0, Cell("cw"))._1
        changedfield = changedfield.set(3, 0, Cell("cb"))._1
        "remove was successful" in {
          changedfield = changedfield.set(0, 3, Cell("cw"))._1
          changedfield = changedfield.set(0, 6, Cell("cw"))._1
          val x = changedfield.removeStone(3, 0)
          x._2 should be(true)
          x._1.cell(3, 0).content.color should be(Color.noColor)
        }
        "was not successful" in {
          val x = changedfield.removeStone(0, 0)
          x._2 should be(false)
          x._1.cell(0, 0).content.color should be(Color.white)
        }
      }
      "should be able to check if there is a mill" should {
        "black mill" in {
          var blackMillField = new Field(normalSize).set(0, 0, Cell("cb"))._1
          blackMillField = blackMillField.set(0, 3, Cell("cb"))._1
          blackMillField = blackMillField.set(0, 6, Cell("cb"))._1
          blackMillField.checkMill(0, 0).millState should be(BlackMillState().handle)
        }
        "white mill" in {
          var whiteMillField = new Field(normalSize).set(0, 0, Cell("cw"))._1
          whiteMillField = whiteMillField.set(0, 3, Cell("cw"))._1
          whiteMillField = whiteMillField.set(0, 6, Cell("cw"))._1
          whiteMillField.checkMill(0, 0).millState should be(WhiteMillState().handle)
        }
        "when its not even possible" in {
          val noMillField = new Field(normalSize)
          noMillField.checkMill(0, 1).millState should be(NoMillState().handle)
        }
      }
      "should create a new field" in {
        val newfield = normalField.createNewField
        newfield.placedBlackStones() should be(0)
      }
    }
  }
}
