package de.htwg.se.mill.controller.controllerStubImpl

import de.htwg.se.mill.controller.controllerComponent.controllerStubImpl.ControllerStub
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerStubSpec extends AnyWordSpec with Matchers {
  "A ControllerStub" when {
    "initialized" should {
      val controllerStub = new ControllerStub
      "have default values" in {
        controllerStub.roundCounter should be(0)
        controllerStub.color should be(1)
        controllerStub.isSet should be(false)
        controllerStub.possiblePosition should be(false)
        controllerStub.winner should be(0)
        controllerStub.winnerText should be("No Winner")
        controllerStub.millState should be("No Mill")
      }
      "create a stubby player" in {
        controllerStub.createPlayer("Kevin", 42) should be("Kevin")
      }
      "create an empty field" in {
        controllerStub.createEmptyField(7)
      }
      "create an random field" in {
        controllerStub.createEmptyField(7)
      }
      "return field as string" in {
        controllerStub.fieldToString({ case Some(value) => value should be("FieldAsString")})
      }
      "return field as html" in {
        controllerStub.fieldToHtml({ case Some(value) => value should be("FieldAsHtml")})
      }
      "return field as html synced" in {
        controllerStub.fieldToHtmlSync should be("FieldAsHtml")
      }
      "return field as json" in {
        controllerStub.fieldToJson({ case Some(value) => value should be("FieldAsJson")})
      }
      "return roundCounter" in {
        controllerStub.getRoundCounter({ case Some(value) => value.toInt should be(0)})
      }
      "handle click" in {
        controllerStub.handleClick(100, 100)({ case Some(value) => value should be("(100,100)")})
      }
      "handle undo, redo, save and load" in {
        controllerStub.changeSaveMethod("db")
        controllerStub.undo()
        controllerStub.redo()
        controllerStub.save()
        controllerStub.load()
      }
      "return color" in {
        controllerStub.color(0, 0)({ case Some(value) => value.toInt should be(1)})
      }
      "return if is set" in {
        controllerStub.isSet(0, 0)({ case Some(value) => value.toBoolean should be(false)})
      }
      "return if is possible position" in {
        controllerStub.possiblePosition(0, 0) should be(false)
      }
      "return field size" in {
        controllerStub.fieldsize should be(7)
      }
      "return a winner" in {
        controllerStub.getWinner({ case Some(value) => value.toInt should be(0)})
      }
      "return a winner text" in {
        controllerStub.getWinnerText({ case Some(value) => value should be("No Winner")})
      }
      "return a mill state" in {
        controllerStub.getMillState({ case Some(value) => value should be("No Mill")})
      }
    }
  }
}
