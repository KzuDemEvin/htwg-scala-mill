package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface}

import scala.swing.FlowPanel.Alignment
import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, FlowPanel, Font, Frame, GridPanel, Label, MainFrame, Orientation, TextField}


class GUI(controller: ControllerInterface) extends MainFrame {


  listenTo(controller)

  title = "Mill"
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel: GridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline: TextField = new TextField(controller.statusText) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }
  val millline: TextField = new TextField(controller.millText) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }
  val roundCounter: TextField = new TextField(controller.getRoundCounter.toString) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }

  val topBar: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += new FlowPanel(Alignment.Left)(millline)
    contents += new FlowPanel(Alignment.Right)(roundCounter)
  }

  contents = new BorderPanel {
    //add(millline, BorderPanel.Position.North)
    //add(roundCounter, BorderPanel.Position.East)
    add(topBar, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  visible = true

  val sizeDim = new Dimension(740, 840)
  size = sizeDim
  centerOnScreen()
  updateField()
  getPlayers


  reactions += {
    case _: CellChanged => updateField()
  }

  def updateField(): Unit = {
    for {
      row <- 0 until controller.fieldsize
      col <- 0 until controller.fieldsize
    } cells(row)(col).redraw()
    statusline.text = controller.statusText
    millline.text = controller.millText
    roundCounter.text = "Round: " + controller.getRoundCounter.toString

    repaint
  }

  def getPlayers: Frame = { new GUIPlayerWindow(controller) }

}