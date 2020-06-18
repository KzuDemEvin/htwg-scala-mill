package de.htwg.se.mill

import org.scalatest.{Matchers, WordSpec}

class MillSpec extends WordSpec with Matchers {
  "The Mill main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      Mill.main(Array[String]("s"))
    }
  }
}
