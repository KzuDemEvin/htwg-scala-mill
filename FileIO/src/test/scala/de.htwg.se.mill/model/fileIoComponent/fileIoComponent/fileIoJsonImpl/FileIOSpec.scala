package de.htwg.se.mill.model.fileIoComponent.fileIoComponent.fileIoJsonImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl.FileIO
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

class FileIOSpec extends AnyWordSpec with Matchers {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  "A JSON FileIO" when {
    "new" should {
      val fileIo = new FileIO
      "Should be able to save the game as JSON" in {
        val jsonFieldString: String = "{  \"field\" : {    \"roundCounter\" : 0,    \"player1Mode\" : \"SetMode\",    \"player2Mode\" : \"SetMode\",    \"cells\" : [ {      \"row\" : 0,      \"col\" : 0,      \"color\" : \"white\"    }, {      \"row\" : 0,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 0,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 0,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 0,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 0,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 0,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 1,      \"color\" : \"white\"    }, {      \"row\" : 1,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 1,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 2,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 3,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 4,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 5,      \"col\" : 6,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 0,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 1,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 2,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 3,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 4,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 5,      \"color\" : \"noColor\"    }, {      \"row\" : 6,      \"col\" : 6,      \"color\" : \"black\"    } ]  }}"
        fileIo.save(jsonFieldString, Some("field_fileIO_Spec.json"))
      }
      "Should be able to load the game" in {
        fileIo.load(Some("field_fileIO_Spec.json")).onComplete {
          case Success(loadedField) => {
            loadedField.contains("roundCounter") should be(true)
            loadedField.contains("player1Mode") should be(true)
            loadedField.contains("player2Mode") should be(true)
          }
          case Failure(_) =>
        }
      }
    }
  }
}
