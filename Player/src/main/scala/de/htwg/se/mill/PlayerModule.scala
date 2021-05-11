package de.htwg.se.mill

import com.google.inject.AbstractModule
import de.htwg.se.mill.controller.PlayerControllerInterface
import de.htwg.se.mill.controller.controllerBaseImpl.PlayerController
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.dbComponent.playerDaoImpl.{PlayerDaoMongo, PlayerDaoSlick}
import net.codingwell.scalaguice.ScalaModule

class PlayerModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[PlayerControllerInterface].to[PlayerController]
    bind[PlayerDaoInterface].annotatedWithName("sql").toInstance(PlayerDaoSlick())
    bind[PlayerDaoInterface].annotatedWithName("mongo").toInstance(PlayerDaoMongo())
  }
}