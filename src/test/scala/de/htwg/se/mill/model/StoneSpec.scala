package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class StoneSpec extends WordSpec with Matchers {
  "A Stone" when {
    "white" should {
      val whiteStone = Stone(0, Color.white)
      "have a color variable value white" in {
        whiteStone.color should be(Color.white)
      }
      "have a color white" in {
        whiteStone.whichColor should be (Color.white)
      }
    }
    "black" should {
      val blackStone = Stone(1, Color.black)
      "have a color variable value black" in {
        blackStone.color should be(Color.black)
      }
      "have a color black" in {
        blackStone.whichColor should be(Color.black)
      }
    }
//    "new" should {
//      val newStone = Stone(0, Color.white)
//      "have value 0" in {
//        newStone.value should be(0)
//      }
//      "not be placed" in {
//        newStone.isSet should be(false)
//      }
//    }
//    "placed in " should {
//      val placedStone = Stone(1, Color.black)
//      "have value 1" in {
//        placedStone.value should be(1)
//      }
//      "is placed" in{
//        placedStone.isSet should be(true)
//      }
//    }
  }
}
