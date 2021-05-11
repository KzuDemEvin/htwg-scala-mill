package de.htwg.se.mill.controller

trait FileIOControllerInterface {
  def changeSaveMethod(method: String): Unit

  def load(filename: Option[String]): String

  def save(fieldInJson: String, filename: Option[String]): Unit

  def saveSqlDb(field: String): Unit

  def loadSqlDb(id: String): String

  def loadAllSqlDb(): Map[Int, String]

  def deleteInSqlDB(id: String): Unit

  def toJson(fields: Map[Int, String]): String
}
