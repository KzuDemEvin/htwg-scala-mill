package roundmanager

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

  val users: Int = 500

  val httpProtocol = http
    .baseUrl("http://localhost:8083")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:88.0) Gecko/20100101 Firefox/88.0")

  val headers_0 = Map(
    "Accept" -> "*/*",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Postman-Token" -> "c73d0373-5d14-414a-a17b-062c8a103756",
    "User-Agent" -> "PostmanRuntime/7.26.8")

  val headers_1 = Map(
    "Accept-Language" -> "en-US,en;q=0.5",
    "DNT" -> "1",
    "Sec-GPC" -> "1",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_17 = Map(
    "Accept" -> "*/*",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Postman-Token" -> "fa9f645e-a246-4d4b-b043-c3f003b21f24",
    "User-Agent" -> "PostmanRuntime/7.26.8")

  val headers_21 = Map(
    "Accept" -> "*/*",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Postman-Token" -> "4000911f-9902-4f82-8cf9-20caa8dee550",
    "User-Agent" -> "PostmanRuntime/7.26.8")

  val headers_26 = Map(
    "Accept" -> "*/*",
    "Accept-Encoding" -> "gzip, deflate, br",
    "Postman-Token" -> "dbab4201-425b-480c-a176-3a7ef0a65458",
    "User-Agent" -> "PostmanRuntime/7.26.8")


  val scn = scenario("RecordedSimulation")
    .exec(http("RecordedSimulation_0:POST_http://localhost:8083/field/createField?size=7")
      .post("/field/createField?size=7")
      .headers(headers_0)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0000_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_1:GET_http://localhost:8083/field/json")
      .get("/field/json")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0001_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_2:GET_http://localhost:8083/field/html")
      .get("/field/html")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0002_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_3:GET_http://localhost:8083/field/string")
      .get("/field/string")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0003_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_4:GET_http://localhost:8083/field/isSet?row=0&col=0")
      .get("/field/isSet?row=0&col=0")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0004_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_5:GET_http://localhost:8083/field/isSet?row=0&col=1")
      .get("/field/isSet?row=0&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0005_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_6:GET_http://localhost:8083/field/isSet?row=1&col=1")
      .get("/field/isSet?row=1&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0006_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_7:GET_http://localhost:8083/field/color?row=1&col=1")
      .get("/field/color?row=1&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0007_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_8:GET_http://localhost:8083/field/color?row=0&col=0")
      .get("/field/color?row=0&col=0")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0008_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_9:GET_http://localhost:8083/field/color?row=0&col=1")
      .get("/field/color?row=0&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0009_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_10:GET_http://localhost:8083/field/possiblePosition?row=0&col=1")
      .get("/field/possiblePosition?row=0&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0010_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_11:GET_http://localhost:8083/field/possiblePosition?row=0&col=0")
      .get("/field/possiblePosition?row=0&col=0")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0011_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_12:GET_http://localhost:8083/field/millState")
      .get("/field/millState")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0012_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_13:GET_http://localhost:8083/turn")
      .get("/turn")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0013_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_14:GET_http://localhost:8083/roundCounter")
      .get("/roundCounter")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0014_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_15:GET_http://localhost:8083/winner")
      .get("/winner")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0015_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_16:GET_http://localhost:8083/winnerText")
      .get("/winnerText")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0016_response.json"))))
    .pause(10)
    .rendezVous(users)
    .exec(http("RecordedSimulation_17:POST_http://localhost:8083/handleClick?row=0&col=0")
      .post("/handleClick?row=0&col=0")
      .headers(headers_17)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0017_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_18:GET_http://localhost:8083/field/color?row=0&col=0")
      .get("/field/color?row=0&col=0")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0018_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_19:GET_http://localhost:8083/turn")
      .get("/turn")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0019_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_20:GET_http://localhost:8083/roundCounter")
      .get("/roundCounter")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0020_response.json"))))
    .pause(7)
    .rendezVous(users)
    .exec(http("RecordedSimulation_21:POST_http://localhost:8083/handleClick?row=0&col=1")
      .post("/handleClick?row=0&col=1")
      .headers(headers_21)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0021_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_22:GET_http://localhost:8083/field/color?row=0&col=1")
      .get("/field/color?row=0&col=1")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0022_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_23:GET_http://localhost:8083/field/color?row=0&col=0")
      .get("/field/color?row=0&col=0")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0023_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_24:GET_http://localhost:8083/turn")
      .get("/turn")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0024_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_25:GET_http://localhost:8083/roundCounter")
      .get("/roundCounter")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0025_response.json"))))
    .pause(7)
    .rendezVous(users)
    .exec(http("RecordedSimulation_26:POST_http://localhost:8083/handleClick?row=0&col=3")
      .post("/handleClick?row=0&col=3")
      .headers(headers_26)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0026_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_27:GET_http://localhost:8083/roundCounter")
      .get("/roundCounter")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0027_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_28:GET_http://localhost:8083/turn")
      .get("/turn")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0028_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_29:GET_http://localhost:8083/field/isSet?row=0&col=3")
      .get("/field/isSet?row=0&col=3")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0029_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_30:GET_http://localhost:8083/field/isSet?row=0&col=6")
      .get("/field/isSet?row=0&col=6")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0030_response.json"))))
    .pause(7)
    .exec(http("RecordedSimulation_31:GET_http://localhost:8083/field/isSet?row=0&col=5")
      .get("/field/isSet?row=0&col=5")
      .headers(headers_1)
      .check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0031_response.json"))))

  setUp(
    scn.inject(rampUsers(users).during(30.seconds))
  ).protocols(httpProtocol)
}

object Field {
  def getField() = repeat(100, "n") {
    exec(http("RecordedSimulation_1:GET_http://localhost:8083/field/json").get("/field/json"))
  }
}