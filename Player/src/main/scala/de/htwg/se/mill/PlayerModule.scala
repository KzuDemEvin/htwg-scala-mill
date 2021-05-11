package de.htwg.se.mill

import com.google.inject.AbstractModule
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.dbComponent.playerDaoImpl.PlayerDaoMongo
import net.codingwell.scalaguice.ScalaModule

class PlayerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[PlayerDaoInterface].to[PlayerDaoMongo]
  }
}