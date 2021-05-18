package de.htwg.se.mill

import com.google.inject.Guice
import de.htwg.se.mill.aview.PlayerHttpServer
import de.htwg.se.mill.controller.PlayerControllerInterface

case object Player {

  val injector = Guice.createInjector(new PlayerModule)
  val controller: PlayerControllerInterface = injector.getInstance(classOf[PlayerControllerInterface])
  val webserver = new PlayerHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
