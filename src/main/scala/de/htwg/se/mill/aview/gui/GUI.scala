package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.{CellChanged, Controller, StoneRemoved}

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, BoxPanel, Color, Dimension, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Orientation, Point, TextField}


class GUI(controller: Controller) extends MainFrame {
  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline = new TextField(controller.statusText, 100) {
    editable = false
  }
  val millline = new TextField(controller.millText, 100) {
    editable = false
  }
  val label = new Label {
    text = "Hallo"
  }


  contents = new BorderPanel {
    //add(millline, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
    add(label, BorderPanel.Position.North)
  }

  visible = true

  val sizeDim = new Dimension(740, 840)
  size = sizeDim
  centerOnScreen()
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
