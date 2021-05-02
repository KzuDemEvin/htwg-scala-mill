package de.htwg.se.mill

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MillSpec extends AnyWordSpec with Matchers {
  "Main should have default field size of 7" in {
    val defaultSize = 7
    Mill.defaultSize should be(defaultSize)
  }
  "Main should accept exit as argument" in {
    Mill.main(Array("exit"))
    Mill.input should be("exit")
  }
//  "The Mill main class" should {
//    "accept text input as argument without readline loop, to test it from command line " in {
//      Mill.main(Array[String]("s"))
//    }
//  }
}
