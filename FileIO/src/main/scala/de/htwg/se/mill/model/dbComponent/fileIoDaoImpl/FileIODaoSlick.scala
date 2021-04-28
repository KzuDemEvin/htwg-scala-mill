package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import slick.dbio.{DBIO, Effect}
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

case class FileIODaoSlick() extends FileIODaoInterface {
  val databaseUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3308") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "fileio") + "?serverTimezone=UTC&useSSL=false"
  val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "FILEIO")

  val database: JdbcBackend.DatabaseDef = Database.forURL(
    url = databaseUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )

  val fileIOTable: TableQuery[FileIOTable] = TableQuery[FileIOTable]

  val setup: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(fileIOTable.schema.createIfNotExists)
  database.run(setup)
  println(s"Settings, databaseUrl: ${databaseUrl}, databaseUser: ${databaseUser}, databasePassword: ${databasePassword}")

  override def save(field: String): Unit = {
    Await.ready(database.run(fileIOTable += (0, field)), Duration.Inf)
  }

  override def load(fieldId: Int): String = {
    val fileIOIdQuery = fileIOTable.filter(_.id === fieldId).result.head
    val fileIO@(id, field) = Await.result(database.run(fileIOIdQuery), Duration.Inf)
    field
  }

  override def load(): Map[Int, String] = {
    var fields = Map.empty[Int, String]
    Await.result(database.run(fileIOTable.result).map(_.foreach {
      case (id, field) => fields += (id -> field)
    }), Duration.Inf)
    fields
  }
}
