package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Cell, Color, Stone}
import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "set with any stone" should {
      val filledCell = Cell(true, Stone("w+"))
      "should be filled with a white Stone" in {
        filledCell.getContent.whichColor should be(Color.white)
      }
      "is filled" in {
        filledCell.isSet should be(true)
      }
    }
    "by default a not filled Cell has no Stone" should {
      val emptyCell = new Cell()
      "should be filled with no Stone" in {
        emptyCell.getContent.whichColor should be(Color.noColor)
      }
      "is not filled" in {
        emptyCell.isSet should be (false)
      }
    }
  }
}
