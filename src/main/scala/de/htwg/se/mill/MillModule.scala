package de.htwg.se.mill


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fileIoComponent._
import net.codingwell.scalaguice.ScalaModule


class MillModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 7

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[FileIOInterface].to[fileIoXmlImpl.FileIO] //XML
    //bind[FileIOInterface].to[fileIoJsonImpl.FileIO] //JSON
  }

}
