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
		.contentTypeHeader("application/json")
		.userAgentHeader("PostmanRuntime/7.28.0")

	val headers_0 = Map("Postman-Token" -> "934d9502-ea30-406e-a316-7f5984dc84cc")

	val headers_1 = Map("Postman-Token" -> "73ed1b3e-489a-4f0b-a57e-a34ae228ce09")

	val headers_2 = Map("Postman-Token" -> "dae0f2ff-ff2a-40c2-82f5-541c2729de27")

	val headers_3 = Map("Postman-Token" -> "3ffa685b-9ec3-42bf-b6bc-666293ee7bd8")

	val headers_4 = Map("Postman-Token" -> "61c037c1-d302-4652-89ad-36ee36888e6c")

	val headers_5 = Map("Postman-Token" -> "496c00ab-1905-4c8a-add2-6ca858b7f71f")

	val headers_6 = Map("Postman-Token" -> "7d19d77e-d4c6-4c85-9df5-1048e1721779")

	val headers_7 = Map("Postman-Token" -> "d9c8c440-4873-419b-9069-827292340730")

	val headers_8 = Map("Postman-Token" -> "e201adc0-8d2f-402a-9534-8352c3ff05e0")



	val scn = scenario("RecordedSimulation")
		// FileIO
		.exec(http("RecordedSimulation_0:GET_http://localhost:8082/")
			.get("/")
			.headers(headers_0)
			.body(RawFileBody("fileIO/recordedsimulation/0000_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0000_response.html"))))
		.pause(6)
		.exec(http("RecordedSimulation_1:POST_http://localhost:8082/fileio")
			.post("/fileio")
			.headers(headers_1)
			.body(RawFileBody("fileIO/recordedsimulation/0001_request.json")))
		.pause(3)
		.exec(http("RecordedSimulation_2:GET_http://localhost:8082/fileio")
			.get("/fileio")
			.headers(headers_2)
			.body(RawFileBody("fileIO/recordedsimulation/0002_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0002_response.json"))))
		.pause(11)
		.exec(http("RecordedSimulation_3:PUT_http://localhost:8082/fileio/db?type=sql")
			.put("/fileio/db?type=sql")
			.headers(headers_3)
			.body(RawFileBody("fileIO/recordedsimulation/0003_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0003_response.json"))))
		.pause(8)
		.exec(http("RecordedSimulation_4:POST_http://localhost:8082/fileio/db")
			.post("/fileio/db")
			.headers(headers_4)
			.body(RawFileBody("fileIO/recordedsimulation/0004_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0004_response.txt"))))
		.pause(10)
		.exec(http("RecordedSimulation_5:GET_http://localhost:8082/fileio/db?id=1")
			.get("/fileio/db?id=1")
			.headers(headers_5)
			.body(RawFileBody("fileIO/recordedsimulation/0005_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0005_response.json"))))
		.pause(13)
		.exec(http("RecordedSimulation_6:PUT_http://localhost:8082/fileio/db?type=mongo")
			.put("/fileio/db?type=mongo")
			.headers(headers_6)
			.body(RawFileBody("fileIO/recordedsimulation/0006_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0006_response.json"))))
		.pause(13)
		.exec(http("RecordedSimulation_7:POST_http://localhost:8082/fileio/db")
			.post("/fileio/db")
			.headers(headers_7)
			.body(RawFileBody("fileIO/recordedsimulation/0007_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0007_response.txt"))))
		.pause(10)
		.exec(http("RecordedSimulation_8:GET_http://localhost:8082/fileio/db?id=609ad0559428d12672bc9105")
			.get("/fileio/db?id=609ad0559428d12672bc9105")
			.headers(headers_8)
			.body(RawFileBody("fileIO/recordedsimulation/0008_request.json"))
			.check(bodyBytes.is(RawFileBody("fileIO/recordedsimulation/0008_response.json"))))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}