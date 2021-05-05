package de.htwg.se.mill.model.dbComponent


trait FileIODaoInterface {

  def save(field: String): Unit

  def load(fileIoID: Option[Int]): String

  def load(): Map[Int, String]

  def delete(fileIoID: Int): Unit

}
