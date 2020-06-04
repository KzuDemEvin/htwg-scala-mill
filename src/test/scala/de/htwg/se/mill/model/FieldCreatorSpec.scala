package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class FieldCreatorSpec extends WordSpec with Matchers {

  "A field" should {
    "create an field filled with cells set to false" in {
      val tinyField = new FieldCreator(7).createField()
      tinyField.cell(0,0).filled should be(false)
    }
      "should throw an exception" in {
        val evenField = new FieldCreator(2)
        an [RuntimeException] should be thrownBy(evenField.createField(2))
      }
  }
}
