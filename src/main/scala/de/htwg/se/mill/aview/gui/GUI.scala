package de.htwg.se.mill.aview.gui

import java.awt.Dimension

import de.htwg.se.mill.controller.{CellChanged, Controller, StoneRemoved}

import scala.swing.event.{Event, Key}
import scala.swing.{Action, BorderPanel, BoxPanel, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Orientation, TextField}

class CellClicked(val row: Int, val column: Int) extends Event

class GUI(controller: Controller) extends MainFrame {
  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar


  val gridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline = new TextField(controller.statusText, 20)


  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  visible = true
  minimumSize = new Dimension(800, 800)
  preferredSize = new Dimension(900, 900)
  updateField

  reactions += {
    case event: CellChanged => updateField
    case event: StoneRemoved => updateField
  }

  def updateField: Unit = {
    for {
      row <-0 until controller.fieldsize
      col <- 0 until controller.fieldsize
    } cells(row)(col).redraw
    statusline.text = controller.statusText
    repaint
  }

}
