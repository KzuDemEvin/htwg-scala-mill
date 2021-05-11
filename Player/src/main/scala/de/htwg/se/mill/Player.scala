package de.htwg.se.mill

import com.google.inject.Guice
import de.htwg.se.mill.aview.PlayerHttpServer
import de.htwg.se.mill.controller.controllerBaseImpl.PlayerController
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface

case object Player {

  val injector = Guice.createInjector(new PlayerModule)
  val controller: PlayerController = new PlayerController(injector.getInstance(classOf[PlayerDaoInterface]))
  val webserver = new PlayerHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
