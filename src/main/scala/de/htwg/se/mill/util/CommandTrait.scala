package de.htwg.se.mill.util

trait CommandTrait {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}