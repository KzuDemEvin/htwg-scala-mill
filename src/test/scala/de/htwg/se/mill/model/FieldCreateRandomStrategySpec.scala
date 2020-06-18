package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class FieldCreateRandomStrategySpec extends WordSpec with Matchers {
  "A FieldCreator " should {
    "create an empty Field and fill it with stones with a creation strategy" in {
      val field= (new FieldCreateRandomStrategy).createNewField(7)
      field.placedStones() should be(24)
      field.available(0,0) should be(false)
    }
  }
}
