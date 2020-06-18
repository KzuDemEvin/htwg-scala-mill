

package de.htwg.se.mill.model

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
        val changedField = normalField.set(0, 0, Cell(true, Stone("w+")))
        changedField.cell(0, 0).getContent.whichColor should be(Color.white)
        normalField.cell(0, 0).getContent.whichColor should be(Color.noColor)
      }
      "should return a string which looks like the gameboard" in {
        var blackwhiteField = normalField.set(0, 0, Cell(true, Stone("w+")))
        blackwhiteField = blackwhiteField.set(1, 1, Cell(true, Stone("b+")))
        blackwhiteField.toString should be("Mill Gameboard:\n w  -  -  o  -  -  o \n -  b  -  o  -  o  - \n -  -  o  o  o  -  - \n o  o  o  -  o  o  o \n -  -  o  o  o  -  - \n -  o  -  o  -  o  - \n o  -  -  o  -  -  o \n")
      }
    }
  }
}
