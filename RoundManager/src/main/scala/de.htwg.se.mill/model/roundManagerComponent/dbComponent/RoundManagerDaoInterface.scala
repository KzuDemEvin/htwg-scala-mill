package de.htwg.se.mill.model.roundManagerComponent.dbComponent

trait RoundManagerDaoInterface {

  def save(player: Player): Unit

  def load(id: Int): Player

  def load(): Map[Int, Player]

}
