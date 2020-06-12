package de.htwg.se.mill.model

import org.scalatest._

class FieldSpec extends WordSpec with Matchers {
  "A field is the playground where you put your mill stones. A field" when {
    "to be created new" should {
      "to be created with a size which displays the number of cells per row/column. Relevant is 7" in {
        val field = new Field(7)
      }
      "to be created with a Matrix of Cells" in {
        val field1 = Field(new Matrix(2, new Cell()))
        val field2 = Field(Matrix[Cell](Vector(Vector(Cell(false, Stone(0, Color.noColor)), Cell(false, Stone(0, Color.noColor))),
                                                Vector(Cell(false, Stone(0, Color.noColor)), Cell(false, Stone(0, Color.noColor))))))
      }
      "created without stones on field" should {
        val field3 = new Field(2)
        val field4 = new Field(7)
        "can give acces to the Cells" in {
          field3.cell(0, 0) should be(Cell(false, Stone(0, Color.noColor)))
          field3.cell(0, 1) should be(Cell(false, Stone(0, Color.noColor)))
          field3.cell(1, 0) should be(Cell(false, Stone(0, Color.noColor)))
          field4.cell(0, 0) should be(Cell(false, Stone(0, Color.noColor)))
          field4.cell(2, 3) should be(Cell(false, Stone(0, Color.noColor)))
          field4.cell(6, 6) should be(Cell(false, Stone(0, Color.noColor)))
        }
        "to ask if an certain position is allowed" in {
          field3.possiblePosition(0, 0) should be(true)
          field3.possiblePosition(0, 1) should be(false)
        }
        "to set a Stone in a Cell of the field" in {
          val modField = field4.set(1, 1, Cell(true, Stone(1, Color.white)))
          modField.cell(1, 1) should be(Cell(true, Stone(1, Color.white)))
        }
        "to ask if a cell is free to put a stone on it" in {
          field4.available(0,0) should be(true)
          val modField2 = field4.set(0,0, Cell(true, Stone(1, Color.white)))
          modField2.available(0,0) should be(false)
        }
      }
    }
    "created with random filled Cells" should {
      val field = Field(new Matrix[Cell](Vector(Vector(Cell(true, Stone(1, Color.white)), Cell(true, Stone(1, Color.black))),
                                                Vector(Cell(true, Stone(1, Color.black)), Cell(true, Stone(1, Color.white))))))
      "have the wished stones in the right cells" in {
        field.cell(0,0) should be(Cell(true, Stone(1, Color.white)))
        field.cell(0,1) should be(Cell(true, Stone(1, Color.black)))
        field.cell(1,0) should be(Cell(true, Stone(1, Color.black)))
        field.cell(1,1) should be(Cell(true, Stone(1, Color.white)))
      }
    }
  }
}
