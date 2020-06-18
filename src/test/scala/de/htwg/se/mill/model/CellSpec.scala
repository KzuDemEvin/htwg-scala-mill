package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "set with any stone" should {
      val filledCell = Cell(true, Stone("w+"))
      "should be filled with a white Stone" in {
        filledCell.content should be(Stone("w+"))
      }
      "is filled" in {
        filledCell.isSet should be(true)
      }
      "has  content" in {
        filledCell.getContent should be(Stone("w+"))
      }
    }
    "by default a not filled Cell has no Stone" should {
      val emptyCell = new Cell()
      "should be filled with no Stone" in {
        emptyCell.content should be(Stone("n"))
      }
      "is not filled" in {
        emptyCell.isSet should be (false)
      }
      "has no content" in {
        emptyCell.getContent should be(Stone("n"))
      }
    }
  }
}
