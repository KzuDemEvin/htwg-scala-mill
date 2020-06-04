package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class StoneSpec extends WordSpec with Matchers {
  "A Stone" when {
    "white" should {
      val whiteStone = Stone(1, Color.white)
      "have a color variable value white" in {
        whiteStone.color should be(Color.white)
      }
      "have a color white" in {
        whiteStone.whichColor should be (Color.white)
      }
      "can be set in a Cell" in {
        whiteStone.isSet should be(true)
      }
    }
    "placed in " should {
      val placedStone = Stone(1, Color.black)
      "is placed" in{
        placedStone.isSet should be(true)
      }
    }
  }

}
