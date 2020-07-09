

package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import de.htwg.se.mill.model.fieldComponent.{Cell, Color}
import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {
  "A Field is the playingfield of Mill. A Field" when {
    "can be constructed in different sizes" in {
      val tinyField = new Field(3)
      val smallField = new Field(5)
      val weirdField = new Field(2)
    }
    "to be constructed in a normal size" should {
      val normalField = new Field(7)
      "have the size 7" in {
        normalField.size should be(7)
      }
      "give access to its cells" in {
        normalField.cell(0, 0).getContent.whichColor should be(Color.noColor)
      }
      "can check if the given position is valid" in {
        normalField.possiblePosition(0, 1) should be(false)
      }
      "can check if the given position is available" in {
        normalField.available(0, 0) should be(true)
      }
      "allow to set individual Cells and remain immutable" in {
        val changedField = normalField.set(0, 0, Cell("cw"))
        changedField.cell(0, 0).getContent.whichColor should be(Color.white)
        normalField.cell(0, 0).getContent.whichColor should be(Color.noColor)
      }

      "should return a string which looks like the gameboard" in {
        var blackwhiteField = normalField.set(0, 0, Cell("cw"))
        blackwhiteField = blackwhiteField.set(1, 1, Cell("cb"))
        blackwhiteField.toString should be("Mill Gameboard:\n w  -  -  o  -  -  o \n -  b  -  o  -  o  - \n -  -  o  o  o  -  - \n o  o  o  -  o  o  o \n -  -  o  o  o  -  - \n -  o  -  o  -  o  - \n o  -  -  o  -  -  o \n")
      }
      "should be able to set and return a roundCounter" in {
        val roundCounter = 3
        normalField.setRoundCounter(roundCounter)
        normalField.getRoundCounter() should be(3)
      }
      "should be able to set and return player 1 in setMode" in {
        normalField.setPlayer1Mode("SetMode")
        normalField.getPlayer1Mode should be("SetMode")
      }
      "should be able to set and return player 1 in moveMode" in {
        normalField.setPlayer1Mode("MoveMode")
        normalField.getPlayer1Mode should be("MoveMode")
      }
      "should be able to set and return player 2 flyMode" in {
        normalField.setPlayer2Mode("FlyMode")
        normalField.getPlayer2Mode should be("FlyMode")
      }
      "should be able to move a stone to a new position" in {
        var changedfield = normalField.set(0,0, Cell("cw"))
        changedfield = changedfield.moveStone(0,0,0,3)
        changedfield.cell(0,3).getContent.whichColor should be(Color.white)
      }
      "should be able to fly with a stone to a new position" in {
        var changedfield = normalField.set(0,0, Cell("cw"))
        changedfield = changedfield.fly(0,0,0,3)
        changedfield.cell(0,3).getContent.whichColor should be(Color.white)
      }
      "should be able to remove a stone, for example when there is a mill" should {
        var changedfield = normalField.set(0, 0, Cell("cw"))
        changedfield = changedfield.set(3,0, Cell("cb"))
        "remove was successfull" in {
          changedfield = changedfield.set(0,3, Cell("cw"))
          changedfield = changedfield.set(0,6 ,Cell("cw"))
          val x = changedfield.removeStone(3, 0)
          x._2 should be(true)
          x._1.cell(3, 0).getContent.whichColor should be(Color.noColor)
        }
        "was not successfull" in {
          val x = changedfield.removeStone(0, 0)
          x._2 should be(false)
          x._1.cell(0, 0).getContent.whichColor should be(Color.white)
        }
      }
      "should be able to check if there is a mill" should {
        "black mill" in {
          var changedfield = normalField.set(0, 0, Cell("cb"))
          changedfield = changedfield.set(0,3, Cell("cb"))
          changedfield = changedfield.set(0,6 ,Cell("cb"))
          changedfield.checkMill(0,0) should be(1)
        }
        "white mill" in {
          var changedfield = normalField.set(0, 0, Cell("cw"))
          changedfield = changedfield.set(0,3, Cell("cw"))
          changedfield = changedfield.set(0,6 ,Cell("cw"))
          changedfield.checkMill(0,0) should be(2)
        }
      }
      "should create a new field" in {
        val newfield = normalField.createNewField
        newfield.placedBlackStones() should be(0)
      }
    }
  }
}