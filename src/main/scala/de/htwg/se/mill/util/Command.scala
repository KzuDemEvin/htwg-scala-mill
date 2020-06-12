package de.htwg.se.mill.util

trait Command {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}
