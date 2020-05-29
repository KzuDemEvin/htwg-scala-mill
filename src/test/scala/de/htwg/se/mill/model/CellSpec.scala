package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "not set with any stone" should {
      val emptyCell = Cell(filled = false)
      "should be filled with false" in {
        emptyCell.filled should be(false)
      }
      "is not filled" in {
        emptyCell.isSet should be(false)
      }
    }
    "set with any stone" should {
      val nonEmptyCell = Cell(filled = true)

      "should be filled with true" in {
        nonEmptyCell.filled should be(true)
      }
      "is filled" in {
        nonEmptyCell.isSet should be(true)
      }
    }
  }

//  "Every Cell" should {
//    "have coordinates" should {
//      val randomCell = Cell(filled = true)
//      "should have coordinates (0,1,2)" in {
//
//      }
//    }
//  }
}
