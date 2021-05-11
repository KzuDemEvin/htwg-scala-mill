package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import slick.dbio.{DBIO, Effect}
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._
import slick.sql.SqlAction

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

case class FileIODaoSlick @Inject() () extends FileIODaoInterface {
  val databaseUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "mill") + "?serverTimezone=UTC&useSSL=false"
  val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "MILL")

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

  override def save(field: String, id: Option[Int] = None): Unit = {
    val ID = id match {
      case Some(ID) => ID
      case None => 0
    }
    Await.ready(database.run(fileIOTable += (ID, field)), Duration.Inf)
  }

  override def load(fileIoID: Option[Int]): String = {
    val fileIOIdQuery: SqlAction[(Int, String), NoStream, Effect.Read] = fileIoID match {
      case Some(fileIoID) => fileIOTable.filter(_.id === fileIoID).result.head
      case None => fileIOTable.sortBy(_.id.desc).take(1).result.head
    }
    val (_, field) = Await.result(database.run(fileIOIdQuery), Duration.Inf)
    field
  }

  override def loadAll(): Map[Int, String] = {
    var fields = Map.empty[Int, String]
    Await.result(database.run(fileIOTable.result).map(_.foreach {
      case (id, field) => fields += (id -> field)
    }), Duration.Inf)
    fields
  }

  override def delete(fileIoID: String): Unit = {
    val query = fileIOTable.filter(_.id === fileIoID.toInt).delete
    database.run(query)
  }
}
