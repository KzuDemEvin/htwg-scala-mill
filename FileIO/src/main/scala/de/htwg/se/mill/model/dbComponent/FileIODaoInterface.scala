package de.htwg.se.mill.model.dbComponent

import scala.concurrent.Future

trait FileIODaoInterface {

  def save(field: String): Unit

  def load(fileIoID: String): Future[String]

  def loadAll(): Future[Seq[(Int, String)]]

  def delete(fileIoID: String): Unit
}
