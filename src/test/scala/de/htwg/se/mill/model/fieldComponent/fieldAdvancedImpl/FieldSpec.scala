package de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl

import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.name.Names
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import org.scalatest.{Matchers, WordSpec}


class FieldSpec extends WordSpec with Matchers {
  val injector: Injector = Guice.createInjector(new MillModule)
  "A Field is the playingfield of Mill. A Field" when {
    val field = injector.instance[FieldInterface](Names.named("normal"))
    val advancedfield = field.createNewField
    "to be constructed in a normal size" should {
      "have the size 7" in {
        advancedfield.size should be(7)
      }
    }
  }
}