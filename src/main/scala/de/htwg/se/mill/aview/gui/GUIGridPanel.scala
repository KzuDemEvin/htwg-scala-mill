package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{Cell, Color, Stone}

import scala.swing.{Button, Graphics2D, GridPanel}

class GUIGridPanel(controller: Controller, allCellPanels: Array[Array[CellPanel]]) {
  def gridPanel: GridPanel = {
    val gridPanel = new GridPanel(controller.fieldsize, controller.fieldsize) {
      for {
        row <- 0 until controller.fieldsize
        col <- 0 until controller.fieldsize
      } {
        if (controller.possiblePosition(row, col)) {
          val cellPanel = new CellPanel(row, col, controller)
          allCellPanels(row)(col) = cellPanel
          contents += cellPanel
          //contents += new Button("w")
          listenTo(cellPanel)
        } else {
          val cellPanel = new CellPanel(row, col, controller)
          allCellPanels(row)(col) = cellPanel
          contents += cellPanel
          //contents += new Button("leer")
        }
      }
    }
    gridPanel
  }
}
