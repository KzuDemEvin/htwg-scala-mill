package fileIO

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://localhost:8082")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val headers_0 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "864391b1-fd24-4afb-bbf3-ceb4a81ac351")

	val headers_1 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "ae750d5b-63e5-4769-8e87-186bb4ea5487")

	val headers_2 = Map("Postman-Token" -> "5f9047eb-3689-449c-9628-129ef3a75875")

	val headers_3 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "c7ff45a9-190c-4fc6-950c-7c23a78ef2bd")

	val headers_4 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "e4f3d30f-bd4f-4ea3-8e1b-427da0eca996")

	val headers_5 = Map("Postman-Token" -> "b77939da-ead7-4a24-ae6b-ce85cce584a4")

	val headers_6 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "e4232db6-5cd2-42e8-af02-a47d300a715b")

	val headers_7 = Map(
		"Content-Type" -> "application/json",
		"Postman-Token" -> "ff0129d4-dcd0-47e4-8ad1-2e23735f8cb5")



	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.post("/fileio")
			.headers(headers_0)
			.body(RawFileBody("fileIO/recordedsimulation/0000_request.json")))
		.pause(4)
		.exec(http("request_1")
			.get("/fileio")
			.headers(headers_1)
			.body(RawFileBody("fileIO/recordedsimulation/0001_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0001_response.json"))))
		.pause(36)
		.exec(http("request_2")
			.put("/fileio/db?type=sql")
			.headers(headers_2)
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0002_response.json"))))
		.pause(12)
		.exec(http("request_3")
			.post("/fileio/db")
			.headers(headers_3)
			.body(RawFileBody("fileIO/recordedsimulation/0003_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0003_response.txt"))))
		.pause(27)
		.exec(http("request_4")
			.get("/fileio/db?id=1")
			.headers(headers_4)
			.body(RawFileBody("fileIO/recordedsimulation/0004_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0004_response.json"))))
		.pause(43)
		.exec(http("request_5")
			.put("/fileio/db?type=mongo")
			.headers(headers_5)
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0005_response.json"))))
		.pause(21)
		.exec(http("request_6")
			.get("/fileio/db?id=609ad0559428d12672bc9105")
			.headers(headers_6)
			.body(RawFileBody("fileIO/recordedsimulation/0006_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0006_response.json"))))
		.pause(6)
		.exec(http("request_7")
			.post("/fileio/db")
			.headers(headers_7)
			.body(RawFileBody("fileIO/recordedsimulation/0007_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0007_response.txt"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}