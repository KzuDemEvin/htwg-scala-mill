package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{PrimaryKey, ProvenShape}

case class CellTable(tag: Tag) extends Table[((Int, Int, Int), Boolean, String)](tag, "Cell") {

  def fieldId: Rep[Int] = column[Int]("fieldId")

  def row: Rep[Int] = column[Int]("row")

  def col: Rep[Int] = column[Int]("col")

  def pk: PrimaryKey = primaryKey("pk_cell", (fieldId, row, col))

  def isSet: Rep[Boolean] = column[Boolean]("isSet")

  def color: Rep[String] = column[String]("color")

  override def * : ProvenShape[((Int, Int, Int), Boolean, String)] = ((fieldId, row, col), isSet, color)
}
