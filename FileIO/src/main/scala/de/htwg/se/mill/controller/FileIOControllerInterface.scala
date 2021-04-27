package de.htwg.se.mill.controller

trait FileIOControllerInterface {

  def load(filename: Option[String]): String

  def save(fieldInJson: String, filename: Option[String]): Unit
}
