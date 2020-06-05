//package de.htwg.se.mill.model
//
//import org.scalatest._
//
//class FieldSpec extends WordSpec with Matchers {
//  "A field" when {
//    "empty" should {
//      val field = new Field[Cell](0, Cell(true))
//    }
//    "filled not with all stones" should {
//      val field = new Field[Cell]()
//    }
//    "filled with all stones" should {
//      val field = new Field[Cell](3, 24)
//      "move stones and return new field" should {
//        val field1 = field.replace(1, 2, 3, Cell(false))
//        field1.coordinates(1,2,3) should be(Cell(false))
//      }
//    }
//  }
//}

//package de.htwg.se.mill.model
//
//import org.scalatest.{Matchers, WordSpec}
//
//class FieldSpec extends WordSpec with Matchers {
//  "A Field is the playingfield of Sudoku. A Field. A Field" when {
//    "to be constructed" should {
//      val tinyField = new Field(1)
//      val smallField = new Field(3)
//      val normalField = new Field(7)
//      val awkwardField = new Field(4)
//    }
//    "for test purposes only created with a Matrix of Cells" in {
//      val awkwardGrid = Field(new Matrix(2, Cell(0)))
//      val testGrid = Grid(Matrix[Cell](Vector(Vector(Cell(0), Cell(0)), Vector(Cell(0), Cell(0)))))
//    }
//  }
//}
