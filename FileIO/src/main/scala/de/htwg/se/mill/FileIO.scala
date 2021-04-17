package de.htwg.se.mill

import de.htwg.se.mill.aview.FileIOHttpServer
import de.htwg.se.mill.controller.FileIOControllerInterface
import de.htwg.se.mill.controller.controllerBaseImpl.FileIOController

case object FileIO {
  val controller: FileIOControllerInterface = new FileIOController
  val webserver = new FileIOHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
