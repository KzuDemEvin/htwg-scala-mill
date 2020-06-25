package de.htwg.se.mill.aview.gui

import scala.swing._

import scala.swing.event._
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.controller.CellChanged
import de.htwg.se.mill.model.Color

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)
  val unavailableColor = new Color(192, 255, 192)
  //val unavailableColor = new Color(238, 238, 238) // backgroundcolor
  val whiteColor = new Color(255, 255, 255)
  val blackColor = new Color(0, 0, 0)

  val sizeDim = new Dimension(100, 100)

  def myCell = controller.cell(row, column)

  // 0 = white, 1 = black, 2 = available, 3 = notValid
  def cellType(row: Int, col: Int): Int = {
    var cellType = 0
    if (controller.possiblePosition(row, col)) {
      if (controller.isSet(row, col)) {
        if (controller.cell(row, col).getContent.whichColor == Color.white) {
          cellType = 0
        } else {
          cellType = 1
        }
      } else {
        cellType = 2
      }
    } else {
      cellType = 3
    }
    cellType
  }

  def cellText(row: Int, col: Int):String = {
    cellType(row, col) match {
      case 0 => ""
      case 1 => ""
      case 2 => ""
      case 3 => ""
    }
  }

  def cellBackground(row: Int, col: Int): Color = {
    cellType(row, col) match {
      case 0 => whiteColor
      case 1 => blackColor
      case 2 => cellColor
      case 3 => unavailableColor
    }
  }

  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 20)
    }

  val setButton = new Button("Set") {
//    minimumSize = sizeDim
//    maximumSize = sizeDim
//    preferredSize = sizeDim
  }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    if (cellType(row, column) != 3) {
      contents += setButton
    }
    preferredSize = sizeDim
    background = cellBackground(row, column)

    listenTo(mouse.clicks)
    listenTo(controller)
    listenTo(setButton)
    reactions += {
      case ButtonClicked(component) if component == setButton => {
        controller.set(row, column)
        repaint
      }
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        repaint
      }
    }
  }

  def redraw:Unit = {
    contents.clear()
    label.text = cellText(row, column)
    cell.background = cellBackground(row, column)
    //setButton.background = cellBackground(row, column)
    contents += cell
    repaint
  }
}