package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class FileIOTable(tag: Tag) extends Table[(Int, String)](tag, "FileIO") {
  def id: Rep[Int] = column[Int]("Id", O.PrimaryKey, O.AutoInc)

  def field: Rep[String] = column[String]("Field")

  def * : ProvenShape[(Int, String)] = (id, field)
}
