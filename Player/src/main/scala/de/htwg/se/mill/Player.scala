package de.htwg.se.mill

import de.htwg.se.mill.aview.PlayerHttpServer
import de.htwg.se.mill.controller.controllerBaseImpl.PlayerController
import de.htwg.se.mill.model.dbComponent.playerDaoImpl.PlayerDaoSlick

case object Player {

  val controller: PlayerController = new PlayerController(new PlayerDaoSlick)
  val webserver = new PlayerHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
