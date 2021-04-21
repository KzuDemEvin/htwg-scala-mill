package de.htwg.se.mill

import com.google.inject.AbstractModule
import de.htwg.se.mill.controller.controllerComponent.ControllerInterface
import net.codingwell.scalaguice.ScalaModule

class MillModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
  }
}
