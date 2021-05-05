package de.htwg.se.mill.model.dbComponent.playerDaoImpl

import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class PlayerTable(tag: Tag) extends Table[(Int, String, Int, String)](tag, "Player") {
  def id: Rep[Int] = column[Int]("Id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("Name")

  def amountStones: Rep[Int] = column[Int]("AmountStones")

  def mode: Rep[String] = column[String]("Mode")

  def * : ProvenShape[(Int, String, Int, String)] = (id, name, amountStones, mode)
}

