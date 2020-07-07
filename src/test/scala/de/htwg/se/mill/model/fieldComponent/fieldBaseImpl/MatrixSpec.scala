package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import de.htwg.se.mill.model.fieldComponent.{Cell, Color}
import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Cell](3, Cell("cw"))
        matrix.size should be(3)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Cell("cw"))))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Cell](2, Cell("cb"))
      "give access to its cells" in {
        matrix.cell(0, 0).getContent.whichColor should be(Color.black)
        matrix.cell(0,0).isSet should be(true)
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Cell("cw"))
        matrix.cell(0, 0).getContent.whichColor should be(Color.black)
        returnedMatrix.cell(0, 0).getContent.whichColor should be(Color.white)
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.refill(Cell("cw"))
        returnedMatrix.cell(0,0).getContent.whichColor should be(Color.white)
      }
    }
  }
}
