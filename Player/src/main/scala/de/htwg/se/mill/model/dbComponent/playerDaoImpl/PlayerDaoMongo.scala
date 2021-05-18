package de.htwg.se.mill.model.dbComponent.playerDaoImpl

import com.google.gson.Gson
import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.playerComponent.Player
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, Observer, SingleObservable}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class PlayerDaoMongo() extends PlayerDaoInterface {
  val uri: String = "mongodb://root:MILL@" + sys.env.getOrElse("MONGODB_HOST", "localhost:27017")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("mill")
  val playerCollection: MongoCollection[Document] = database.getCollection("Player")
  val gson = new Gson()

  override def save(player: Player): Unit = {
    printf(s"Saving player ${player.name} in MongoDB\n")
    val doc: Document = Document("_id" -> 0, "name" -> player.name, "amountStones" -> player.amountStones, "mode" -> player.mode)

    val insertObservable: SingleObservable[InsertOneResult] = playerCollection.insertOne(doc)
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = print(s"inserted: $result\n")
      override def onError(e: Throwable): Unit = print(s"failed: $e\n")
      override def onComplete(): Unit = print("completed\n")
    })
  }

  override def load(id: Int): Player = {
    printf(s"Loading player $id in MongoDB\n")
    val json: JsValue = Json.parse(Await.result(playerCollection.find(equal("_id", id)).first().head(), Duration.Inf).toJson())
    val name = (json \ "name").get.toString().replaceAll("\"", "")
    val amountStones = (json \ "amountStones").get.toString().toInt
    val mode = (json \ "mode").get.toString().replaceAll("\"", "")
    Player.apply(name, amountStones).changeMode(mode)
  }

  override def load(): Map[Int, Player] = {
    printf(s"Loading players in MongoDB\n")
    val json: JsValue = Json.parse(Await.result(playerCollection.find().head(), Duration.Inf).toJson())
    println(json)
    Map.empty[Int, Player]
  }
}
