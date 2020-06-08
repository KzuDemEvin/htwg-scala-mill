package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class FieldCreatorSpec extends WordSpec with Matchers {

  "A field" should {
    "create an field filled with cells set to false" in {
      val tinyField = new FieldCreator().createField(7)
      tinyField.cell(0,0).filled should be(false)
    }
      "should change its size from even to odd" in {
        val notEvenField = new FieldCreator().createField(6)
        notEvenField.size should be(7)
      }
  }

}
