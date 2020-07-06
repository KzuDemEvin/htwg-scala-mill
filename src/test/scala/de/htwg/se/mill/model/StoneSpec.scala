package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.{Color, Stone}
import org.scalatest.{Matchers, WordSpec}

class StoneSpec extends WordSpec with Matchers {

  "A stone" when {
    "created" should {
      "be a placed white stone" in {
        val placedWhitestone = Stone("w+")
        placedWhitestone.whichColor should be (Color.white)
        placedWhitestone.isSet should be(true)
      }
      "be a non placed white stone" in {
        val nonPlacedWhitestone = Stone("w-")
        nonPlacedWhitestone.isSet should be(false)
      }
      "be a placed black stone" in {
        val placedBlackstone = Stone("b+")
        placedBlackstone.whichColor should be(Color.black)
        placedBlackstone.isSet should be(true)
      }
      "be a non placed black stone" in {
        val nonPlacedBlackstone = Stone("b-")
        nonPlacedBlackstone.isSet should be(false)
      }
      "be a non placed colorless stone" in {
        val nonPlacedColorlessstone = Stone("n")
        nonPlacedColorlessstone.whichColor should be(Color.noColor)
        nonPlacedColorlessstone.isSet should be(false)
      }
    }
  }

}
