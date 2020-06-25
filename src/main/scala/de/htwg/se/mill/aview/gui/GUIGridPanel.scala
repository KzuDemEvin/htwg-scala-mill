package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{Cell, Color, Stone}

import scala.swing.{Button, GridPanel}

class GUIGridPanel(controller: Controller) {
  def gridPanel: GridPanel = {
    val gridPanel = new GridPanel(controller.fieldsize, controller.fieldsize) {
      for {
        row <- 0 until controller.fieldsize
        column <- 0 until controller.fieldsize
      } {
        if (controller.possiblePosition(row, column)) {
          if (controller.cell(row, column).content.whichColor == Color.white) {
            contents += new Button("w")
          } else if (controller.cell(row, column).content.whichColor == Color.black) {
            contents += new Button("b")
          } else {
            contents += new Button("o") {
              controller.set(row, column, Cell(true, Stone("w+")))
            }
          }
        } else {
          contents += new Button("-")
        }
      }
    }
    gridPanel
  }
}
