package de.htwg.se.mill.controller

import de.htwg.se.mill.model.{Cell, Field, FieldCreator}
import de.htwg.se.mill.util.Observable

class Controller(var field:Field) extends Observable {
  def createEmptyField(size: Int): Unit = {
    field = new Field(size)
    notifyObservers
  }

  def createRandomField(size: Int, amoutStones:Int): Unit = {
    field = new FieldCreator().fillRandomly(size, amoutStones)
    notifyObservers
  }

  def fieldToString: String = field.toString

  def set(row: Int, col: Int, c: Cell): Unit = {
    field = field.set(row, col, c)
    notifyObservers
  }
}
