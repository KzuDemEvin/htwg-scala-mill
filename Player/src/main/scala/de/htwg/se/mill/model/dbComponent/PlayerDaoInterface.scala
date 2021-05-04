package de.htwg.se.mill.model.dbComponent

import de.htwg.se.mill.model.playerComponent.Player

trait PlayerDaoInterface {

  def save(player: Player): Unit

  def load(id: Int): Player

  def load(): Map[Int, Player]

}
