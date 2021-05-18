package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RandomStrategySpec extends AnyWordSpec with Matchers {
  "A FieldCreator " should {
    "create an empty Field and fill it with stones with a creation strategy" in {
      val field = (new RandomStrategy).createNewField(7)
      field.placedStones() should be(18)
    }
  }
}
