package de.htwg.se.mill

import de.htwg.se.mill.aview.PlayerHttpServer
import de.htwg.se.mill.controller.controllerBaseImpl.PlayerController

case object Player {
  val controller: PlayerController = new PlayerController
  val webserver = new PlayerHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
