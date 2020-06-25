package de.htwg.se.mill.aview.gui

import scala.swing._
import javax.swing.table._

import scala.swing.event._
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.controller.CellChanged
import de.htwg.se.mill.model.{Cell, Stone}

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {

  val givenCellColor = new Color(200, 200, 255)
  val cellColor = new Color(224, 224, 255)
  val highlightedCellColor = new Color(192, 255, 192)

  def myCell = controller.cell(row, column)

  val button = new Button("Set") {
    controller.set(row, column)
  }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += button
    preferredSize = new Dimension(100, 100)
    background = highlightedCellColor
    //background = if (controller.available(row, column)) givenCellColor else cellColor
    //border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
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
      //setBackground(cell)
    contents += cell
    repaint
  }

//  def setBackground(p: Panel) = p.background = if (controller.isAvailable(row, column)) givenCellColor
}