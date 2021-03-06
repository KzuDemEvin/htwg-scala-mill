package de.htwg.se.mill


import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.aview.FileIOHttpServer
import de.htwg.se.mill.controller.FileIOControllerInterface

case object FileIO {
  val injector: Injector = Guice.createInjector(new FileIOModule)
  val controller: FileIOControllerInterface = injector.getInstance(classOf[FileIOControllerInterface])
  val webserver = new FileIOHttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
