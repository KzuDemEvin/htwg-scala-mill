package de.htwg.se.mill.model.playerComponent.fileIoXmlImpl

import de.htwg.se.mill.model.fieldComponent.Cell
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.fileIoComponent.fileIoXmlImpl.FileIO
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {
  "A FileIO" when {
    "new" should {
      var field = new Field(7)
      val fileIo = new FileIO
      "Should be able to save the game" in {
        fileIo.save(field)
      }
      "Should be able to load the game" in {
        fileIo.load
      }
      "Should be able to save the game again" in {
        field = new Field(7)
        field = field.set(0, 0, Cell("cw"))
        field = field.set(6, 6, Cell("cb"))
        fileIo.saveXML(field)
      }
      "Should be able to load the game again" in {
        fileIo.load
      }
    }
  }
}
