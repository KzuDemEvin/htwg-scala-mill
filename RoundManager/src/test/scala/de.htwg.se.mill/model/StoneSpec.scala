package de.htwg.se.mill.model

import de.htwg.se.mill.model.fieldComponent.{Color, Stone, StoneTrait}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StoneSpec extends AnyWordSpec with Matchers {

  "A stone" when {
    "Interface" should {
      "have only isSet attribute" in {
        val stone: StoneTrait = new StoneTrait {
          override def isSet: Boolean = true
        }
        stone.isSet should be(true)
      }
    }
    "created" should {
      "be a placed white stone" in {
        val placedWhitestone = Stone("w+")
        placedWhitestone.isInstanceOf[Stone] should be(true)
        placedWhitestone.color should be(Color.white)
        placedWhitestone.isSet should be(true)
      }
      "be a non placed white stone" in {
        val nonPlacedWhitestone = Stone("w-")
        nonPlacedWhitestone.isInstanceOf[Stone] should be(true)
        nonPlacedWhitestone.color should be(Color.white)
        nonPlacedWhitestone.isSet should be(false)
      }
      "be a placed black stone" in {
        val placedBlackstone = Stone("b+")
        placedBlackstone.isInstanceOf[Stone] should be(true)
        placedBlackstone.color should be(Color.black)
        placedBlackstone.isSet should be(true)
      }
      "be a non placed black stone" in {
        val nonPlacedBlackstone = Stone("b-")
        nonPlacedBlackstone.isInstanceOf[Stone] should be(true)
        nonPlacedBlackstone.color should be(Color.black)
        nonPlacedBlackstone.isSet should be(false)
      }
      "be a non placed colorless stone" in {
        val nonPlacedColorlessstone = Stone("n")
        nonPlacedColorlessstone.isInstanceOf[Stone] should be(true)
        nonPlacedColorlessstone.color should be(Color.noColor)
        nonPlacedColorlessstone.isSet should be(false)
      }
    }
  }

}
