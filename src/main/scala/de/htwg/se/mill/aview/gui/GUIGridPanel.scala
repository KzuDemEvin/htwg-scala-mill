package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import scala.swing.GridPanel

class GUIGridPanel(controller: ControllerInterface, allCellPanels: Array[Array[CellPanel]]) {
  def gridPanel: GridPanel = {
    val gridPanel = new GridPanel(controller.fieldsize, controller.fieldsize) {

      for {
        row <- 0 until controller.fieldsize
        col <- 0 until controller.fieldsize
      } {
        val cellPanel = new CellPanel(row, col, controller)
        allCellPanels(row)(col) = cellPanel
        contents += cellPanel
        if (controller.possiblePosition(row, col)) {
          listenTo(cellPanel)
        }
      }
      //vGap_=(0)
      //hGap_=(0)
    }
    gridPanel
  }
}
