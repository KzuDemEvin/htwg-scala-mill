package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class StoneSpec extends WordSpec with Matchers {
  "A Stone" when { "new" should {
      val newStone = Stone(0, "white")
      "have value 0" in {
        newStone.value should be(0)
      }
      "not be placed" in {
        newStone.isSet should be(false)
      }
      "have a color variable value white" in {
        newStone.color should be("white")
      }
      "have a color white" in {
        newStone.whichColor should be ("white")
      }
   }
    "placed" should {
      val placedStone = Stone(1, "black")
      "have value 1" in {
        placedStone.value should be(1)
      }
      "be placed" in {
        placedStone.isSet should be(true)
      }
      "have a color variable value black" in {
        placedStone.color should be("black")
      }
      "have a color black" in {
        placedStone.whichColor should be("black")
      }
    }
  }
}
