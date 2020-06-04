package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Cell](3, Cell(true, Stone(1, Color.white)))
        matrix.size should be(3)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Cell(true, Stone(1, Color.white)))))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Cell](2, Cell(false, Stone(0, Color.black)))
      "give access to its cells" in {
        matrix.cell(0, 0) should be(Cell(false, Stone(0, Color.black)))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Cell(true, Stone(1, Color.white)))
        matrix.cell(0, 0) should be(Cell(false, Stone(0, Color.black)))
        returnedMatrix.cell(0, 0) should be(Cell(true, Stone(1, Color.white)))
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.refill(Cell(true, Stone(0, Color.white)))
        returnedMatrix.cell(0,0) should be(Cell(true, Stone(0, Color.white)))
      }
    }
  }
}
