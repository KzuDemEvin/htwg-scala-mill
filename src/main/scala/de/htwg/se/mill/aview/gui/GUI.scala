package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface, FieldChanged, TwoCellsChanged}

import scala.swing.FlowPanel.Alignment
import scala.swing.{BorderPanel, BoxPanel, Dimension, FlowPanel, Font, Frame, GridPanel, MainFrame, Orientation, TextField}


class GUI(controller: ControllerInterface) extends MainFrame {


  listenTo(controller)

  title = "Mill"
  val cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)
  var drawUnusedCells: Boolean = true


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel: GridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline: TextField = new TextField(controller.gameState) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }

  val millline: TextField = new TextField("", 7) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }
  controller.getMillState({ case Some(millState) => millline.text = millState })

  val roundCounter: TextField = new TextField("", 6) {
    font = Font("Dialog", Font.Bold, 16)
    editable = false
  }
  controller.getRoundCounter({ case Some(rc) => roundCounter.text = rc })

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

  val sizeDim: Dimension = new Dimension(708, 840)
  size = sizeDim
  centerOnScreen()
  updateField()
  getPlayers


  reactions += {
    case _: CellChanged => updateField((-1, -1))
    case twoCellsChanged: TwoCellsChanged => updateField(cell = twoCellsChanged.cell)
    case _: FieldChanged => updateField()
  }

  private def updateField(cell: (Int, Int) = (-2, -2)): Unit = {
    cell match {
      case (-2, -2) =>
        for {
          row <- 0 until controller.fieldsize
          col <- 0 until controller.fieldsize
          if drawUnusedCells || controller.possiblePosition(row, col)
        } cells(row)(col).redraw()
      case (-1, -1) =>
      case (row, col) => cells(row)(col).redraw()
    }
    drawUnusedCells = false
    controller.getMillState({ case Some(millState) => millline.text = millState })
    controller.getRoundCounter({ case Some(rc) => roundCounter.text = "Round: " + rc })
    repaint
    statusline.text = controller.gameState
  }

  def getPlayers: Frame = {
    new GUIPlayerWindow(controller)
  }

}