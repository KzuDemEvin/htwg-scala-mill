package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.{Cell, Color, Stone}
import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "filled with a white stone" should {
      val filledWhiteCell = Cell("cw")
      "should be filled with a white Stone" in {
        filledWhiteCell.getContent.whichColor should be(Color.white)
      }
      "is filled" in {
        filledWhiteCell.isSet should be(true)
      }
      "have the String method" in {
        filledWhiteCell.toString should be("White Stone")
      }
    }
    "filled with a black stone" should {
      val filledBlackCell = Cell("cb")
      "should be filled with a black Stone" in {
        filledBlackCell.getContent.whichColor should be(Color.black)
      }
      "is filled" in {
        filledBlackCell.isSet should be(true)
      }
      "have the String method" in {
        filledBlackCell.toString should be("Black Stone")
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
      "have the String method" in {
        emptyCell.toString should be("No Stone")
      }
    }
  }
}
