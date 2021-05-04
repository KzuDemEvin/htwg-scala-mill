package de.htwg.se.mill.model.roundManagerComponent.dbComponent.daoImpl

import de.htwg.se.mill.model.roundManagerComponent.dbComponent.RoundManagerDaoInterface

import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class RoundManagerDaoSlick() extends RoundManagerDaoInterface {
  val databaseUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3308") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "player") + "?serverTimezone=UTC&useSSL=false"
  val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "ROUNDMANAGER")

  val database: JdbcBackend.DatabaseDef = Database.forURL(
    url = databaseUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )

  val playerTable: TableQuery[RoundManagerTable] = TableQuery[RoundManagerTable]

  val setup: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(playerTable.schema.createIfNotExists)
  database.run(setup)
  println(s"Settings, databaseUrl: ${databaseUrl}, databaseUser: ${databaseUser}, databasePassword: ${databasePassword}")

  override def save(player: Player): Unit = {
    Await.ready(database.run(playerTable += (0, player.name, player.amountStones, player.mode)), Duration.Inf)
  }

  override def load(playerId: Int): Player = {
    val playerIdQuery = playerTable.filter(_.id === playerId).result.head
    val player@(id, name, amountStones, mode) = Await.result(database.run(playerIdQuery), Duration.Inf)
    Player.apply(name, amountStones).changeMode(mode)
  }

  override def load(): Map[Int, Player] = {
    var players = Map.empty[Int, Player]
    Await.result(database.run(playerTable.result).map(_.foreach {
      case (id, name, amountStones, mode) => players += (id -> Player.apply(name, amountStones).changeMode(mode))
    }), Duration.Inf)
    players
  }
}
