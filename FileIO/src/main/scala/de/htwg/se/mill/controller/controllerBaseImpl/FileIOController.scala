package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.FileIOModule
import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.{Guice, Inject, Injector}

class FileIOController @Inject() (var daoInterface: FileIODaoInterface) extends FileIOControllerInterface {
  val fileIO: FileIO = new FileIO
  val injector: Injector = Guice.createInjector(new FileIOModule)

  def changeSaveMethod(method: String): Unit = {
    method match {
      case "mongo" => injector.instance[FileIODaoInterface](Names.named("mongo"))
      case _ => injector.instance[FileIODaoInterface](Names.named("sql"))
    }
  }

  override def load(filename: Option[String]): String = fileIO.load(filename)

  override def save(fieldInJson: String, filename: Option[String]): Unit = fileIO.save(fieldInJson, filename)

  override def saveSqlDb(field: String, id: Option[Int]): Unit = daoInterface.save(field, id)

  override def loadSqlDb(id: Option[Int]): String = daoInterface.load(id)

  override def loadAllSqlDb(): Map[Int, String] = daoInterface.loadAll()

  override def deleteInSqlDB(id: Int): Unit = daoInterface.delete(id)

  override def toJson(fields: Map[Int, String]): String = {
    new Gson().toJson(fields)
  }
}
