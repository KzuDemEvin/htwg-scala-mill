package de.htwg.se.mill.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.mill.controller._
import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "Mill"
  var cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)

  def highlightpanel:FlowPanel = new FlowPanel {
    contents += new Label("Highlight:")
    for {index <- 0 to controller.gridSize} {
      val button = Button(if (index == 0) "" else index.toString) {
        controller.highlight(index)
      }
      button.preferredSize_=(new Dimension(30, 30))
      contents += button
      listenTo(button)
    }
  }

  def gridPanel:GridPanel = new GridPanel(controller.fieldsize, controller.fieldsize) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.ORANGE
    for {
      row <- 0 until controller.fieldsize
      col <- 0 until controller.fieldsize
    } {
      if ()
      contents += new FlowPanel {
        border = LineBorder(java.awt.Color.BLACK, 2)
        val cellPanel = new CellPanel(row, col, controller)
        cells(row)(col) = cellPanel
        contents += cellPanel
        listenTo(cellPanel)
      }
    }
  }
  val statusline = new TextField(controller.statusText, 20)

  contents = new BorderPanel {
    add(highlightpanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New Game") { controller.createEmptyField(7) })
      contents += new MenuItem(Action("Random") { controller.createRandomField(7) })
      contents += new MenuItem(Action("Exit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }

    contents += new Menu("Highlight") {
      mnemonic = Key.H
      for { index <- 0 to controller.gridSize } {
        contents += new MenuItem(Action(index.toString) { controller.highlight(index) })
      }
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Show all Stones per Player") { controller.toggleShowAllCandidates })
    }
  }

  visible = true
  redraw

  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged     => redraw
    case event: CandidatesChanged => redraw
  }

  def resize(gridSize: Int) = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(highlightpanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
  }
  def redraw = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw
    statusline.text = controller.statusText
    repaint
  }
}