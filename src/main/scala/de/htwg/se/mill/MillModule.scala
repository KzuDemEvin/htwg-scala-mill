package de.htwg.se.mill

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.mill.controller.controllerComponent.ControllerInterface
import net.codingwell.scalaguice.ScalaModule

class MillModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bindConstant().annotatedWith(Names named "savefio").to( s"http://%s/fileio")
    bindConstant().annotatedWith(Names named "savedb").to(s"http://%s/field/json")
    bindConstant().annotatedWith(Names named "loadfio").to(s"http://%s/fileio")
    bindConstant().annotatedWith(Names named "loaddb").to(s"http://%s/fileio/db?id=%d")
  }
}
