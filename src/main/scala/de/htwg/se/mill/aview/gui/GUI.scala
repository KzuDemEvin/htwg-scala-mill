package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.{CandidatesChanged, CellChanged, Controller}

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, BoxPanel, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Orientation, TextField}


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
  updateField

  reactions += {
    case event: CellChanged     => updateField
    case event: CandidatesChanged => updateField
  }

  def updateField: Unit = {
    statusline.text = controller.statusText
    repaint
  }

}
