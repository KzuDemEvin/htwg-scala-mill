package de.htwg.se.mill.model.fileIoComponent.fileIoComponent.fileIoXmlImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.fileIoComponent.fileToXmlImpl.FileIO
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

class FileIOSpec extends AnyWordSpec with Matchers {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  "A XML FileIO" when {
    "new" should {
      val fileIo = new FileIO
      "Should be able to save the game as XML" in {
        val xmlFieldString: String = "<field roundCounter=\"0\" player1Mode=\"SetMode\" player2Mode=\"SetMode\">\n    <cell row=\"0\" col=\"0\"> noColor </cell>\n    <cell row=\"0\" col=\"1\"> noColor </cell>\n    <cell row=\"0\" col=\"2\"> noColor </cell>\n    <cell row=\"0\" col=\"3\"> noColor </cell>\n    <cell row=\"0\" col=\"4\"> noColor </cell>\n    <cell row=\"0\" col=\"5\"> noColor </cell>\n    <cell row=\"0\" col=\"6\"> noColor </cell>\n    <cell row=\"1\" col=\"0\"> noColor </cell>\n    <cell row=\"1\" col=\"1\"> noColor </cell>\n    <cell row=\"1\" col=\"2\"> noColor </cell>\n    <cell row=\"1\" col=\"3\"> noColor </cell>\n    <cell row=\"1\" col=\"4\"> noColor </cell>\n    <cell row=\"1\" col=\"5\"> noColor </cell>\n    <cell row=\"1\" col=\"6\"> noColor </cell>\n    <cell row=\"2\" col=\"0\"> noColor </cell>\n    <cell row=\"2\" col=\"1\"> noColor </cell>\n    <cell row=\"2\" col=\"2\"> noColor </cell>\n    <cell row=\"2\" col=\"3\"> noColor </cell>\n    <cell row=\"2\" col=\"4\"> noColor </cell>\n    <cell row=\"2\" col=\"5\"> noColor </cell>\n    <cell row=\"2\" col=\"6\"> noColor </cell>\n    <cell row=\"3\" col=\"0\"> noColor </cell>\n    <cell row=\"3\" col=\"1\"> noColor </cell>\n    <cell row=\"3\" col=\"2\"> noColor </cell>\n    <cell row=\"3\" col=\"3\"> noColor </cell>\n    <cell row=\"3\" col=\"4\"> noColor </cell>\n    <cell row=\"3\" col=\"5\"> noColor </cell>\n    <cell row=\"3\" col=\"6\"> noColor </cell>\n    <cell row=\"4\" col=\"0\"> noColor </cell>\n    <cell row=\"4\" col=\"1\"> noColor </cell>\n    <cell row=\"4\" col=\"2\"> noColor </cell>\n    <cell row=\"4\" col=\"3\"> noColor </cell>\n    <cell row=\"4\" col=\"4\"> noColor </cell>\n    <cell row=\"4\" col=\"5\"> noColor </cell>\n    <cell row=\"4\" col=\"6\"> noColor </cell>\n    <cell row=\"5\" col=\"0\"> noColor </cell>\n    <cell row=\"5\" col=\"1\"> noColor </cell>\n    <cell row=\"5\" col=\"2\"> noColor </cell>\n    <cell row=\"5\" col=\"3\"> noColor </cell>\n    <cell row=\"5\" col=\"4\"> noColor </cell>\n    <cell row=\"5\" col=\"5\"> noColor </cell>\n    <cell row=\"5\" col=\"6\"> noColor </cell>\n    <cell row=\"6\" col=\"0\"> noColor </cell>\n    <cell row=\"6\" col=\"1\"> noColor </cell>\n    <cell row=\"6\" col=\"2\"> noColor </cell>\n    <cell row=\"6\" col=\"3\"> noColor </cell>\n    <cell row=\"6\" col=\"4\"> noColor </cell>\n    <cell row=\"6\" col=\"5\"> noColor </cell>\n    <cell row=\"6\" col=\"6\"> noColor </cell>\n</field>"
        fileIo.save(xmlFieldString, Some("field_fileIO_Spec.xml"))
      }
      "Should be able to load the game" in {
        fileIo.load(Some("field_fileIO_Spec.xml")).onComplete {
          case Success(loadedField) => {
            loadedField.contains("roundCounter") should be(true)
            loadedField.contains("player1Mode") should be(true)
            loadedField.contains("player2Mode") should be(true)
          }
          case Failure(_) => print(_)
        }
      }
    }
  }
}
