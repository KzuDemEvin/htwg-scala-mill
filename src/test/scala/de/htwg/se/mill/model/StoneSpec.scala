package de.htwg.se.mill.model

import org.scalatest.{Matchers, WordSpec}

class StoneSpec extends WordSpec with Matchers {
  "A Stone" when {
    "new" should {
      val newStone = Stone(false, "white")
      "have a value set to false" in {
        newStone.value should be(false)
      }
      "have a color" in {
        val stoneWhite = Stone(false, "white")
        val stoneBlack = Stone(false, "black")
        "which is white" in {
          stoneWhite.color should be("white")
        }
        "which is black" in
          stoneBlack.toString should be("Your Name")
      }
    }
    "is placed" should {
        val stone = Stone(true, "white")
        "have a value set to true" in {
          stone.value should be(true)
        }
      "have a color" in {
        val stoneWhite = Stone(false, "white")
        val stoneBlack = Stone(false, "black")
        "which is white" in {
          stoneWhite.color should be("white")
        }
        "which is black" in
          stoneBlack.toString should be("Your Name")
      }
    }
  }
}
