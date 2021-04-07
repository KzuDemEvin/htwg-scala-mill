package de.htwg.se.mill.model.fileIoComponent.fileIoComponent.fileIoJsonImpl

import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {
  "A JSON FileIO" when {
    "new" should {
      val normalSize = 7
      var savedField = new Field(normalSize)
      val fileIo = new FileIO
      "Should be able to save the game as JSON" in {
        savedField = new Field(normalSize)
        savedField = savedField.set(0, 0, Cell("cw"))
        savedField = savedField.set(6, 6, Cell("cb"))
        savedField = savedField.set(1, 1, Cell("cw"))
        savedField.placedWhiteStones() should be(2)
        savedField.placedBlackStones() should be(1)
        fileIo.save(savedField, Some("field_fileIO_Spec.json"))
      }
      "Should be able to load the game" in {
        val loadedField = fileIo.load(Some("field_fileIO_Spec.json"))
        loadedField.placedWhiteStones() should be(2)
        loadedField.placedBlackStones() should be(1)
      }
    }
  }
}
