package de.htwg.se.mill.model.dbComponent


trait FileIODaoInterface {

  def save(field: String, id: Option[Int] = None): Unit

  def load(fileIoID: Option[Int]): String

  def loadAll(): Map[Int, String]

  def delete(fileIoID: Int): Unit

}
