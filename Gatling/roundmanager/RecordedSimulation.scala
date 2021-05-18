package roundmanager

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8083")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:88.0) Gecko/20100101 Firefox/88.0")

	val headers_0 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "c6223e37-358b-4099-963e-04f96613f4b5",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_1 = Map(
		"Accept-Language" -> "en-US,en;q=0.5",
		"DNT" -> "1",
		"Sec-GPC" -> "1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "4668ff41-4108-4ee1-88ea-fdc112942585",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_15 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "61de4445-547b-49c8-8350-bb040210bb63",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_16 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "9d4938c5-1fd0-43ab-b342-13a8a0951412",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_17 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "b2d1bfff-1067-478a-9d19-7b34e97953b4",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_18 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "24377b2b-a9c9-4e04-aac9-a5516f3605a0",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_19 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "1ecff18d-81d2-4253-ac49-5d4b8d19d448",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_20 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "db3c9cf8-d5cf-4dc3-bd37-f918f7c42bc5",
		"User-Agent" -> "PostmanRuntime/7.26.8")

	val headers_21 = Map(
		"Accept" -> "*/*",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Postman-Token" -> "c9ebdcc1-66e7-41a4-9e9f-c00a0f1cb362",
		"User-Agent" -> "PostmanRuntime/7.26.8")



	val scn = scenario("RecordedSimulation")
		// field
		.exec(http("RecordedSimulation_0:POST_http://localhost:8083/field/createRandomField?size=7")
			.post("/field/createRandomField?size=7")
			.headers(headers_0)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0000_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_1:GET_http://localhost:8083/field/json")
			.get("/field/json")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0001_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_2:GET_http://localhost:8083/field/html")
			.get("/field/html")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0002_response.txt"))))
		.pause(5)
		.exec(http("RecordedSimulation_3:GET_http://localhost:8083/field/string")
			.get("/field/string")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0003_response.txt"))))
		.pause(5)
		.exec(http("RecordedSimulation_4:POST_http://localhost:8083/field/createField?size=7")
			.post("/field/createField?size=7")
			.headers(headers_4)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0004_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_5:GET_http://localhost:8083/field/isSet?row=0&col=0")
			.get("/field/isSet?row=0&col=0")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0005_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_6:GET_http://localhost:8083/field/color?row=0&col=0")
			.get("/field/color?row=0&col=0")
			.headers(headers_1)
			.check(status.is(500))
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0006_response.txt"))))
		.pause(5)
		.exec(http("RecordedSimulation_7:GET_http://localhost:8083/field/color?row=0&col=1")
			.get("/field/color?row=0&col=1")
			.headers(headers_1)
			.check(status.is(500))
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0007_response.txt"))))
		.pause(5)
		.exec(http("RecordedSimulation_8:GET_http://localhost:8083/field/possiblePosition?row=0&col=0")
			.get("/field/possiblePosition?row=0&col=0")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0008_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_9:GET_http://localhost:8083/field/possiblePosition?row=0&col=1")
			.get("/field/possiblePosition?row=0&col=1")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0009_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_10:GET_http://localhost:8083/field/millState")
			.get("/field/millState")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0010_response.json"))))
		.pause(5)
		// play
		.exec(http("RecordedSimulation_11:GET_http://localhost:8083/turn")
			.get("/turn")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0011_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_12:GET_http://localhost:8083/roundCounter")
			.get("/roundCounter")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0012_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_13:GET_http://localhost:8083/winner")
			.get("/winner")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0013_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_14:GET_http://localhost:8083/winnerText")
			.get("/winnerText")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0014_response.json"))))
		.pause(5)
		// testGame
		.exec(http("RecordedSimulation_16:POST_http://localhost:8083/handleClick?row=0&col=0")
			.post("/handleClick?row=0&col=0")
			.headers(headers_16)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0016_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_17:POST_http://localhost:8083/handleClick?row=0&col=3")
			.post("/handleClick?row=0&col=3")
			.headers(headers_17)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0017_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_18:POST_http://localhost:8083/handleClick?row=3&col=0")
			.post("/handleClick?row=3&col=0")
			.headers(headers_18)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0018_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_19:POST_http://localhost:8083/handleClick?row=0&col=6")
			.post("/handleClick?row=0&col=6")
			.headers(headers_19)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0019_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_20:POST_http://localhost:8083/handleClick?row=6&col=0")
			.post("/handleClick?row=6&col=0")
			.headers(headers_20)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0020_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_21:POST_http://localhost:8083/handleClick?row=0&col=3")
			.post("/handleClick?row=0&col=3")
			.headers(headers_21)
			.check(bodyBytes.is(RawFileBody("roundmanager/recordedsimulation/0021_response.json"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}