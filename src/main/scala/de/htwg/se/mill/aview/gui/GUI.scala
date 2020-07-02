package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.CellChanged
import de.htwg.se.mill.controller.StoneRemoved
import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, BoxPanel, Color, Dimension, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Orientation, Point, TextField}


class GUI(controller: Controller) extends MainFrame {
  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline = new TextField(controller.statusText, 100) { editable = false }
  val millline = new TextField(controller.millText, 100) { editable = false }


  contents = new BorderPanel {
    add(millline, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  visible = true

  val sizeDim = new Dimension(740, 840)
  size = sizeDim
  centerOnScreen()
  updateField


  reactions += {
    case event: CellChanged => updateField
  }

  def updateField: Unit = {
    for {
      row <-0 until controller.fieldsize
      col <- 0 until controller.fieldsize
    } cells(row)(col).redraw
    statusline.text = controller.statusText
    millline.text = controller.millState
    repaint
  }

}
