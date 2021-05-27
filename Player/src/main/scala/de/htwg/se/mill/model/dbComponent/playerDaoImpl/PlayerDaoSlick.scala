package de.htwg.se.mill.model.dbComponent.playerDaoImpl

import de.htwg.se.mill.model.dbComponent.PlayerDaoInterface
import de.htwg.se.mill.model.playerComponent.Player
import slick.dbio.{DBIO, Effect}
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

case class PlayerDaoSlick() extends PlayerDaoInterface {
  val databaseUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "mill") + "?serverTimezone=UTC&useSSL=false"
  val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "MILL")

  val database: JdbcBackend.DatabaseDef = Database.forURL(
    url = databaseUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )

  val playerTable: TableQuery[PlayerTable] = TableQuery[PlayerTable]

  val setup: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(playerTable.schema.createIfNotExists)
  database.run(setup)

  override def save(player: Player): Unit = {
    printf(s"Saving player ${player.name} in MySQL\n")
    Await.ready(database.run(playerTable += (0, player.name, player.amountStones, player.mode)), Duration.Inf)
  }

  override def load(playerId: Int): Future[Any] = {
    printf(s"Loading player $playerId in MySQL\n")
    val playerIdQuery = playerTable.filter(_.id === playerId).result.head
    database.run(playerIdQuery)
  }

  override def load(): Map[Int, Player] = {
    printf(s"Loading players in MySQL\n")
    var players = Map.empty[Int, Player]
    Await.result(database.run(playerTable.result).map(_.foreach {
      case (id, name, amountStones, mode) => players += (id -> Player.apply(name, amountStones).changeMode(mode))
    }), Duration.Inf)
    players
  }
}
