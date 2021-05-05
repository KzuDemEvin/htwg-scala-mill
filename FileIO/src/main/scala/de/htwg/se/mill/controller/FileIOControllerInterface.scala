package de.htwg.se.mill.controller

trait FileIOControllerInterface {

  def load(filename: Option[String]): String

  def save(fieldInJson: String, filename: Option[String]): Unit

  def saveSqlDb(field: String): Unit

  def loadSqlDb(id: Option[Int]): String

  def loadAllSqlDb(): Map[Int, String]

  def deleteInSqlDB(id: Int): Unit

  def toJson(fields: Map[Int, String]): String
}
