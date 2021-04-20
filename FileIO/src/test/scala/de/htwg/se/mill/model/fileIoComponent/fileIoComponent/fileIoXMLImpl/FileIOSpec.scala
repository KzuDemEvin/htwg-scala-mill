package de.htwg.se.mill.model.fileIoComponent.fileIoComponent.fileIoXMLImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.fileIoComponent.fileIoXmlImpl.FileIO
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {
  "A XML FileIO" when {
    "new" should {
      var savedField = new Field(7)
      val fileIo = new FileIO
      "Should be able to save the game as XML" in {
        savedField = new Field(7)
        savedField = savedField.set(0, 0, Cell("cw"))._1
        savedField = savedField.set(6, 6, Cell("cb"))._1
        savedField = savedField.set(1, 1, Cell("cw"))._1
        savedField.placedWhiteStones() should be(2)
        savedField.placedBlackStones() should be(1)
        fileIo.save(savedField, Some("field_fileIO_Spec.xml"))
      }
      "Should be able to load the game" in {
        val loadedField = fileIo.load(Some("field_fileIO_Spec.xml"))
        loadedField.placedWhiteStones() should be(2)
        loadedField.placedBlackStones() should be(1)
      }
      "Should be able to save the game again" in {
        fileIo.saveXML(savedField, "field_fileIO_Spec2.xml")
      }
    }
  }
}
