package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface}
import scala.swing.{BorderPanel, Dimension, MainFrame, TextField}


class GUI(controller: ControllerInterface) extends MainFrame {
  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline = new TextField(controller.statusText, 100) { editable = false }
  val millline = new TextField(controller.millText, 96) { editable = false }
  val roundCounter = new TextField(controller.roundCounter.toString, 4) { editable = false}

   val topBar = new BorderPanel {
    add(millline, BorderPanel.Position.West)
     add(roundCounter, BorderPanel.Position.East)
  }


  contents = new BorderPanel {
    add(topBar, BorderPanel.Position.North)
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
    millline.text = controller.millText
    roundCounter.text = controller.roundCounter.toString
    repaint
  }

}
