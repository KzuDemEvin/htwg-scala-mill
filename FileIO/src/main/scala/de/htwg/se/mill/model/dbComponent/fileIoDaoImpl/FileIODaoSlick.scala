package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import slick.dbio.{DBIO, Effect}
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._
import slick.sql.SqlAction

import scala.concurrent.{ExecutionContextExecutor, Future}

case class FileIODaoSlick() extends FileIODaoInterface {
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

  override def save(field: String): Unit = {
    printf(s"Saving file in MySQL\n")
    database.run(fileIOTable += (0, field))
  }

  override def load(fileIoID: String): Future[String] = {
    printf(s"Loading file $fileIoID in MySQL\n")
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    val fileIOIdQuery: SqlAction[(Int, String), NoStream, Effect.Read] = fileIoID.toIntOption match {
      case Some(fileIoID) => fileIOTable.filter(_.id === fileIoID.toInt).result.head
      case None => fileIOTable.sortBy(_.id.desc).take(1).result.head
    }
    database.run(fileIOIdQuery).map[String](_._2)
  }

  override def loadAll(): Future[Seq[(Int, String)]] = {
    printf(s"Loading files in MySQL\n")
    database.run(fileIOTable.result)
  }

  override def delete(fileIoID: String): Unit = {
    printf(s"Deleting file $fileIoID in MySQL\n")
    val query = fileIOTable.filter(_.id === fileIoID.toInt).delete
    database.run(query)
  }
}
