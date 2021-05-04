package de.htwg.se.mill.model.dbComponent

trait FileIODaoInterface {

  def save(field: String): Unit

  def load(fieldId: Int): String

  def load(): Map[Int, String]

}
