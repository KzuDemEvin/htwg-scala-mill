package de.htwg.se.mill.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.mill.controller._
import de.htwg.se.mill.model.Color

import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

//  listenTo(controller)
//
//  title = "Mill"
//  var cells = Array.ofDim[CellPanel](controller.fieldSize, controller.fieldSize)
//
//
//  val gridPanel = new GridPanel(controller.fieldSize, controller.fieldSize) {
//    for {
//      row <- 0 until controller.fieldSize
//      column <- 0 until controller.fieldSize
//    } {
//      if (controller.possiblePosition(row, column)) {
//        if (controller.cell(row, column).content.whichColor == Color.white) {
//          contents += new Button("w")
//        } else if (controller.cell(row, column).content.whichColor == Color.black) {
//          contents += new Button("b")
//        } else {
//          contents += new Button {
//            text = "o"
//            enabled = true
//          }
//        }
//      } else {
//        contents += new Button("-")
//      }
//  }


//  def gridPanel = new GridPanel(controller.fieldSize, controller.fieldSize) {
//    border = LineBorder(java.awt.Color.BLACK, 2)
//    background = java.awt.Color.BLACK
//    for {
//      row <- 0 until controller.fieldSize
//      column <- 0 until controller.fieldSize
//    } {
//      if (controller.possiblePosition(row, column)) {
//        if (controller.cell(row, column).content.whichColor == Color.white) {
//          gridPanel += Button("w")
//        } else if (controller.cell(row, column).content.whichColor == Color.black) {
//          gridPanel += Button("b")
//        } else {
//          gridPanel += Button("o")
//        }
//      } else {
//        gridPanel += Button("-")
//      }
//    }
//    listenTo(gridPanel)
//  }

//  def gridPanel = new GridPanel(controller.blockSize, controller.blockSize) {
//    border = LineBorder(java.awt.Color.BLACK, 2)
//    background = java.awt.Color.BLACK
//    for {
//      outerRow <- 0 until controller.blockSize
//      outerColumn <- 0 until controller.blockSize
//    } {
//      contents += new GridPanel(controller.blockSize, controller.blockSize) {
//        border = LineBorder(java.awt.Color.BLACK, 2)
//        for {
//          innerRow <- 0 until controller.blockSize
//          innerColumn <- 0 until controller.blockSize
//        } {
//          val x = outerRow * controller.blockSize + innerRow
//          val y = outerColumn * controller.blockSize + innerColumn
//          val cellPanel = new CellPanel(x, y, controller)
//          cells(x)(y) = cellPanel
//          contents += cellPanel
//          listenTo(cellPanel)
//        }
//      }
//    }
//  }
//  val statusline = new TextField(controller.statusText, 20)
//
//  contents = new BorderPanel {
//    add(gridPanel, BorderPanel.Position.Center)
//    add(statusline, BorderPanel.Position.South)
//  }
//
//  menuBar = new MenuBar {
//    contents += new Menu("File") {
//      mnemonic = Key.F
//      contents += new MenuItem(Action("New") { controller.createEmptyField(controller.fieldSize) })
//      contents += new MenuItem(Action("Random") { controller.createRandomField(controller.fieldSize) })
//      contents += new MenuItem(Action("Quit") { System.exit(0) })
//    }
//    contents += new Menu("Edit") {
//      mnemonic = Key.E
//      contents += new MenuItem(Action("Undo") { controller.undo })
//      contents += new MenuItem(Action("Redo") { controller.redo })
//    }
//  }
//
//  visible = true
//  redraw
//
//  reactions += {
//    case event: CellChanged     => redraw
//    case event: CandidatesChanged => redraw
//  }
//
//  def redraw = {
//    for {
//      row <- 0 until controller.fieldSize
//      column <- 0 until controller.fieldSize
//    } cells(row)(column).redraw
//    statusline.text = controller.statusText
//    repaint
//  }
}