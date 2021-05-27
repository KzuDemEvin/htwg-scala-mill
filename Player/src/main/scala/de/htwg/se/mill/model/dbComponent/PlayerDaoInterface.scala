package de.htwg.se.mill.model.dbComponent

import de.htwg.se.mill.model.playerComponent.Player

import scala.concurrent.Future

trait PlayerDaoInterface {

  def save(player: Player): Unit

  def load(id: Int): Future[Any]

  def load(): Map[Int, Player]

}
