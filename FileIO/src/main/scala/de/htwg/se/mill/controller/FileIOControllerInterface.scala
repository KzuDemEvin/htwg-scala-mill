package de.htwg.se.mill.controller

trait FileIOControllerInterface {
  def changeSaveMethod(method: String): Unit

  def load(filename: Option[String]): String

  def save(fieldInJson: String, filename: Option[String]): Unit

  def saveSqlDb(field: String, id: Option[Int]): Unit

  def loadDb(id: String): String

  def loadAllDb(): Map[Int, String]

  def deleteInDB(id: String): Unit

  def toJson(fields: Map[Int, String]): String
}
