package de.htwg.se.mill

import com.google.inject.AbstractModule
import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.controller.controllerBaseImpl.FileIOController
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import de.htwg.se.mill.model.dbComponent.fileIoDaoImpl._
import net.codingwell.scalaguice.ScalaModule

class FileIOModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[FileIOControllerInterface].to[FileIOController]
    bind[FileIODaoInterface].annotatedWithName("sql").toInstance(FileIODaoSlick())
    bind[FileIODaoInterface].annotatedWithName("mongo").toInstance(FileIODaoMongo())
  }
}
