package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.{Cell, Stone}
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Color
import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "set with any stone" should {
      val filledCell = Cell("cw")
      "should be filled with a white Stone" in {
        filledCell.getContent.whichColor should be(Color.white)
      }
      "is filled" in {
        filledCell.isSet should be(true)
      }
    }
    "by default a not filled Cell has no Stone" should {
      val emptyCell = Cell("ce")
      "should be filled with no Stone" in {
        emptyCell.getContent.whichColor should be(Color.noColor)
      }
      "is not filled" in {
        emptyCell.isSet should be (false)
      }
    }
  }
}
