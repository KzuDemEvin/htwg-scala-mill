package de.htwg.se.mill.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.mill.controller._
import de.htwg.se.mill.model.Color

import scala.io.Source._

//class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)



  val statusline = new TextField(controller.statusText, 20)
  val gridPanel = new GUIGridPanel(controller, cells).gridPanel

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  visible = true
  updateField

  reactions += {
    case event: CellChanged     => updateField
  }

  def updateField: Unit = {
    statusline.text = controller.statusText
    repaint
  }
}