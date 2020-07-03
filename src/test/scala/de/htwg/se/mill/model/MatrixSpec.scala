package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Cell, Color, Matrix, Stone}
import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[Cell](3, fieldBaseImpl.Cell(true, Stone("w+")))
        matrix.size should be(3)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(fieldBaseImpl.Cell(true, Stone("w+")))))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Cell](2, fieldBaseImpl.Cell(true, Stone("b+")))
      "give access to its cells" in {
        matrix.cell(0, 0).getContent.whichColor should be(Color.black)
        matrix.cell(0,0).isSet should be(true)
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, fieldBaseImpl.Cell(true, Stone("w+")))
        matrix.cell(0, 0).getContent.whichColor should be(Color.black)
        returnedMatrix.cell(0, 0).getContent.whichColor should be(Color.white)
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.refill(fieldBaseImpl.Cell(true, Stone("w+")))
        returnedMatrix.cell(0,0).getContent.whichColor should be(Color.white)
      }
    }
  }
}
