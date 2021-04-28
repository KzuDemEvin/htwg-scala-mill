package de.htwg.se.mill.util

import de.htwg.se.mill.model.roundManagerComponent.RoundManager

class UndoManager {
  private var undoStack: List[RoundManager] = Nil
  private var redoStack: List[RoundManager] = Nil

  def doStep(mgr: RoundManager): Unit = undoStack = mgr :: undoStack

  def undoStep(): Option[RoundManager] =
    undoStack match {
      case Nil | List() => None
      case head :: undoState :: stack =>
        undoStack = stack
        redoStack = head :: redoStack
        Some(undoState)
      case _ => None
    }

  def redoStep(): Option[RoundManager] = {
    redoStack match {
      case Nil => None
      case head :: stack =>
        redoStack = stack
        undoStack = head :: undoStack
        Some(head)
    }
  }
}
