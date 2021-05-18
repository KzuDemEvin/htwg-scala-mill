package player

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8081")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val headers_0 = Map("Postman-Token" -> "1e63999c-4acf-4bda-a894-ada572699acd")

	val headers_1 = Map("Postman-Token" -> "ae93b5d3-cb75-4ad2-b5f6-075cc578b4a0")

	val headers_2 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "411e5f56-237d-4383-a0ac-c549619d3bdb")

	val headers_3 = Map("Postman-Token" -> "5636416d-6aad-454a-9e4d-6eaf97a3025b")

	val headers_4 = Map("Postman-Token" -> "8d796552-c073-4142-9d80-8c6f2cc8ec25")

	val headers_5 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "a5edf9bf-f925-42c0-add6-c2986fe5abb1")

	val headers_6 = Map("Postman-Token" -> "23680859-ca0a-4a75-aa2d-4f8eff12e873")

	val headers_7 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "6861a687-65d9-45b5-97a2-65ce450058cf")

	val headers_8 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "3e89a20b-30ab-4349-8c5b-1aad719fb228")

	val headers_9 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "63298017-855e-435f-9a8b-148831ba5f91")

	val headers_10 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "3cee6335-278a-45b5-91e0-ca0be6631ba7")



	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0000_response.html"))))
		.pause(12)
		.exec(http("request_1")
			.get("/player?number=1")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0001_response.json"))))
		.pause(18)
		.exec(http("request_2")
			.post("/player?number=1&name=TestUser")
			.headers(headers_2)
			.body(RawFileBody("player/recordedsimulation/0002_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0002_response.json"))))
		.pause(8)
		.exec(http("request_3")
			.get("/player?number=1")
			.headers(headers_3)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0003_response.json"))))
		.pause(4)
		.exec(http("request_4")
			.get("/player?number=2")
			.headers(headers_4)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0004_response.json"))))
		.pause(14)
		.exec(http("request_5")
			.put("/player?number=1&mode=Flymode")
			.headers(headers_5)
			.body(RawFileBody("player/recordedsimulation/0005_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0005_response.json"))))
		.pause(8)
		.exec(http("request_6")
			.get("/player?number=1")
			.headers(headers_6)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0006_response.json"))))
		.pause(13)
		.exec(http("request_7")
			.get("/player/name?number=1")
			.headers(headers_7)
			.body(RawFileBody("player/recordedsimulation/0007_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0007_response.json"))))
		.pause(17)
		.exec(http("request_8")
			.put("/player/db?type=sql")
			.headers(headers_8)
			.body(RawFileBody("player/recordedsimulation/0008_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0008_response.json"))))
		.pause(9)
		.exec(http("request_9")
			.get("/player/db?id=2&number=2")
			.headers(headers_9)
			.body(RawFileBody("player/recordedsimulation/0009_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0009_response.json"))))
		.pause(11)
		.exec(http("request_10")
			.get("/player?number=2")
			.headers(headers_10)
			.body(RawFileBody("player/recordedsimulation/0010_request.json"))
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0010_response.json"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}