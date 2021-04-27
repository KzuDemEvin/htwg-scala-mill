package de.htwg.se.mill.controller.controllerBaseImpl

import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO

class FileIOController extends FileIOControllerInterface {
  val fileIO = new FileIO

  override def load(filename: Option[String]): String = {
    fileIO.load(filename)
  }

  override def save(fieldInJson: String, filename: Option[String]): Unit = {
    fileIO.save(fieldInJson, filename)
  }
}
