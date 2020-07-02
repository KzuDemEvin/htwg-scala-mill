package de.htwg.se.mill


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Field, RandomStrategy}
import de.htwg.se.mill.model.fieldComponent.FieldInterface


class MillModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 7

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[FieldInterface].to[Field]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[FieldInterface].annotatedWithName("normal").toInstance(new Field(defaultSize))
    bind[FieldInterface].annotatedWithName("random").toInstance((new RandomStrategy).createNewField(defaultSize))
  }

}
