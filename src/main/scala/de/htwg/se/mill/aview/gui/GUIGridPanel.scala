package de.htwg.se.mill.aview.gui

import java.awt.image.BufferedImage

import de.htwg.se.mill.controller.Controller
import javax.swing.ImageIcon

import scala.swing.{Frame, GridPanel, Label}

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
          listenTo(cellPanel)
        } else {
          val cellPanel = new CellPanel(row, col, controller)
          allCellPanels(row)(col) = cellPanel
          contents += cellPanel
        }
      }
    }
    gridPanel
  }

  def toLabel: Label = {
    val label = new Label{
      gridPanel
    }
    label
  }
}
