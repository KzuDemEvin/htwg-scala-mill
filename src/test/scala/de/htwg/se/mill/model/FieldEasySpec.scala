package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class FieldEasySpec extends WordSpec with Matchers {
  "A FieldEasy is a simple vector which include Cells. A FieldEasy" when {
    "empty" should {
      "be created with a size and a sample cell" in {
        val fieldEasy = new FieldEasy[Cell](2, Cell(false))
        fieldEasy.size should be(2)
      }
      "for tests only ---> vector of vector" in {
        val testFieldEasy = FieldEasy(Vector(Vector(Cell(true))))
        testFieldEasy.size should be(1)
      }
    }
  }
  "filled" should {
    val fieldEasy = new FieldEasy[Cell](2, Cell(true))
    "gives information about its cells" in {
      fieldEasy.cell(0) should be(Cell(true))
    }
    "replace cells and return a new Vector" in {
      val changedField = fieldEasy.replaceCell(0, Cell(false))
      fieldEasy.cell(0) should be(Cell(true))
      changedField.cell(0) should be(Cell(false))
    }
    "be filled" in {
      val changedField = fieldEasy.refill(Cell(false))
      changedField.cell(0) should be(Cell(false))
    }
  }
}
