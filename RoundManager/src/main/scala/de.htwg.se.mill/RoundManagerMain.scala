package de.htwg.se.mill

import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.aview.RoundManagerHttpServer
import de.htwg.se.mill.controller.controllerRoundManager.RoundManagerControllerInterface

object RoundManagerMain {
  val defaultSize = 7
  val injector: Injector = Guice.createInjector(new RoundManagerModule)
  val controller: RoundManagerControllerInterface = injector.getInstance(classOf[RoundManagerControllerInterface])
  val webserver = new RoundManagerHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}