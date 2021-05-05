package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO

class FileIOController(daoInterface: FileIODaoInterface) extends FileIOControllerInterface {
  val fileIO = new FileIO

  override def load(filename: Option[String]): String = fileIO.load(filename)

  override def save(fieldInJson: String, filename: Option[String]): Unit = fileIO.save(fieldInJson, filename)

  override def saveSqlDb(field: String): Unit = daoInterface.save(field)

  override def loadSqlDb(id: Int): String = daoInterface.load(id)

  override def loadSqlDb(): Map[Int, String] = daoInterface.load()

  override def deleteInSqlDB(id: Int): Unit = daoInterface.delete(id)

  override def toJson(fields: Map[Int, String]): String = {
    new Gson().toJson(fields)
  }
}
