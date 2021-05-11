package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.FileIOModule
import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO
import net.codingwell.scalaguice.InjectorExtensions._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class FileIOController extends FileIOControllerInterface {
  val fileIO: FileIO = new FileIO
  val injector: Injector = Guice.createInjector(new FileIOModule)
  var daoInterface: FileIODaoInterface = injector.instance[FileIODaoInterface](Names.named("mongo"))

  override def changeSaveMethod(method: String): Unit = {
    method match {
      case "mongo" => daoInterface = injector.instance[FileIODaoInterface](Names.named("mongo"))
      case _ => daoInterface = injector.instance[FileIODaoInterface](Names.named("sql"))
    }
  }

  override def load(filename: Option[String]): String = fileIO.load(filename)

  override def save(fieldInJson: String, filename: Option[String]): Unit = fileIO.save(fieldInJson, filename)

  override def saveDb(field: String): Unit = daoInterface.save(field)

  override def loadDb(id: String): String = {
    Await.result(daoInterface.load(id), Duration.Inf)
  }

  override def loadAllDb(): Map[Int, String] = Await.result(daoInterface.loadAll(), Duration.Inf).toMap[Int, String]

  override def deleteInDB(id: String): Unit = daoInterface.delete(id)

  override def toJson(fields: Map[Int, String]): String = {
    new Gson().toJson(fields)
  }
}
