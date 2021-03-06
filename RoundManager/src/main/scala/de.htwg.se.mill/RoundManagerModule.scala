package de.htwg.se.mill

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.mill.controller.controllerRoundManager.{RoundManagerController, RoundManagerControllerInterface}
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.RandomStrategy
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl.Field
import net.codingwell.scalaguice.ScalaModule

class RoundManagerModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 7

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[FieldInterface].to[Field]
    bind[RoundManagerControllerInterface].to[RoundManagerController]

    bind[FieldInterface].annotatedWithName("normal").toInstance(new Field(defaultSize))
    bind[FieldInterface].annotatedWithName("random").toInstance((new RandomStrategy).createNewField(defaultSize))
  }
}
