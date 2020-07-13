package de.htwg.se.mill.model.playerComponent.fileIoJsonImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {
  "A FileIO" when {
    "new" should {
      var savedField = new Field(7)
      val fileIo = new FileIO
      "Should be able to save the game" in {
        savedField = new Field(7)
        savedField = savedField.set(0, 0, Cell("cw"))
        savedField = savedField.set(6, 6, Cell("cb"))
        savedField = savedField.set(1, 1, Cell("cw"))
        savedField.placedWhiteStones() should be(2)
        savedField.placedBlackStones() should be(1)
        fileIo.save(savedField)
      }
      "Should be able to load the game" in {
        val loadedField = fileIo.load
        loadedField.placedWhiteStones() should be(2)
        loadedField.placedBlackStones() should be(1)
      }
    }
  }
}
