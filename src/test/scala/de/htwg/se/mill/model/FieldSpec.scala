

package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.{Cell, Color, Stone, fieldBaseImpl}
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
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
    }
  }
}
