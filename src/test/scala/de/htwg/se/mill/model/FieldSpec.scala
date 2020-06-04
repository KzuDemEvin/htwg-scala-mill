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
//
//    }
//  }
//}
