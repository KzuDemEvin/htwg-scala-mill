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

	val headers_0 = Map("Postman-Token" -> "d989fb26-86c7-4c56-afcf-4e4121f24578")

	val headers_1 = Map("Postman-Token" -> "681464db-bed8-44bb-8b77-e8e32e2dd8e1")

	val headers_2 = Map("Postman-Token" -> "37dbcd41-d348-47ce-ac59-2e76b8a59d6e")

	val headers_3 = Map("Postman-Token" -> "c8806f18-d0d7-4aff-a2c4-15bc4fc9eabf")

	val headers_4 = Map("Postman-Token" -> "5ce8fb7f-bd4e-4d03-8b2d-f39d830fa926")

	val headers_5 = Map("Postman-Token" -> "0ffa213f-bb2d-43c4-9e26-e6e44dadb43f")

	val headers_6 = Map("Postman-Token" -> "3e0d163f-812f-4d01-8066-0a10f7844bb9")

	val headers_7 = Map("Postman-Token" -> "7233b802-5380-4fbc-a193-ef6bbc8d24a9")

	val headers_8 = Map("Postman-Token" -> "392eca8b-f974-4eb5-bcac-96d76116909f")

	val headers_9 = Map("Postman-Token" -> "155bd048-e4d0-4924-bcb4-e3eac228180f")

	val headers_10 = Map("Postman-Token" -> "6285cedf-5578-4b57-baa0-d6e9dee01817")

	val headers_11 = Map("Postman-Token" -> "a3390ab4-3241-4001-b7a7-820b32a778cb")

	val headers_12 = Map("Postman-Token" -> "acb50f88-c41a-409a-93da-94904c6f6739")

	val headers_13 = Map("Postman-Token" -> "225ffb1d-0f7f-4a46-902e-058289829f3d")

	val headers_14 = Map("Postman-Token" -> "64c41b20-9935-4be5-bee5-dd6e5bb73853")



	val scn = scenario("RecordedSimulation")
		// player
		.exec(http("RecordedSimulation_0:GET_http://localhost:8081/")
			.get("/")
			.headers(headers_0)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0000_response.html"))))
		.pause(9)
		.exec(http("RecordedSimulation_1:POST_http://localhost:8081/player?number=1&name=TestUser")
			.post("/player?number=1&name=TestUser")
			.headers(headers_1)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0001_response.json"))))
		.pause(10)
		.exec(http("RecordedSimulation_2:PUT_http://localhost:8081/player?number=1&mode=FlyMode")
			.put("/player?number=1&mode=FlyMode")
			.headers(headers_2)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0002_response.json"))))
		.pause(5)
		.exec(http("RecordedSimulation_3:GET_http://localhost:8081/player?number=1")
			.get("/player?number=1")
			.headers(headers_3)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0003_response.json"))))
		.pause(4)
		.exec(http("RecordedSimulation_4:DELETE_http://localhost:8081/player?number=1")
			.delete("/player?number=1")
			.headers(headers_4)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0004_response.json"))))
		.pause(2)
		.exec(http("RecordedSimulation_5:GET_http://localhost:8081/player?number=1")
			.get("/player?number=1")
			.headers(headers_5)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0005_response.json"))))
		.pause(17)
		.exec(http("RecordedSimulation_6:POST_http://localhost:8081/player?number=1&name=TestUser")
			.post("/player?number=1&name=TestUser")
			.headers(headers_6)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0006_response.json"))))
		.pause(16)
		.exec(http("RecordedSimulation_7:PUT_http://localhost:8081/player/db?type=mongo")
			.put("/player/db?type=mongo")
			.headers(headers_7)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0007_response.json"))))
		.pause(82)
		.exec(http("RecordedSimulation_8:GET_http://localhost:8081/player/db?number=2&id=60b655aa82e4027db16a2243")
			.get("/player/db?number=2&id=60b655aa82e4027db16a2243")
			.headers(headers_8)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0008_response.json"))))
		.pause(10)
		.exec(http("RecordedSimulation_9:GET_http://localhost:8081/player?number=2")
			.get("/player?number=2")
			.headers(headers_9)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0009_response.json"))))
		.pause(40)
		.exec(http("RecordedSimulation_10:PUT_http://localhost:8081/player/db?type=sql")
			.put("/player/db?type=sql")
			.headers(headers_10)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0010_response.json"))))
		.pause(77)
		.exec(http("RecordedSimulation_11:POST_http://localhost:8081/player/db?number=2")
			.post("/player/db?number=2")
			.headers(headers_11)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0011_response.json"))))
		.pause(21)
		.exec(http("RecordedSimulation_12:GET_http://localhost:8081/player/db?number=2&id=1")
			.get("/player/db?number=2&id=1")
			.headers(headers_12)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0012_response.json"))))
		.pause(18)
		.exec(http("RecordedSimulation_13:GET_http://localhost:8081/player?number=2")
			.get("/player?number=2")
			.headers(headers_13)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0013_response.json"))))
		.pause(10)
		.exec(http("RecordedSimulation_14:GET_http://localhost:8081/player/name?number=2")
			.get("/player/name?number=2")
			.headers(headers_14)
			.check(bodyBytes.is(RawFileBody("player/recordedsimulation/0014_response.json"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}