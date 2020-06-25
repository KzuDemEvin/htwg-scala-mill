package de.htwg.se.mill.aview.gui

import scala.swing._
import javax.swing.table._
import scala.swing.event._
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.controller.CellChanged

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {
//
//  val givenCellColor = new Color(200, 200, 255)
//  val cellColor = new Color(224, 224, 255)
//  val highlightedCellColor = new Color(192, 255, 192)
//
//  def myCell = controller.cell(row, column)
//
//  def cellText(row: Int, col: Int) = if (controller.isSet(row, column)) " " + controller.cell(row, column).content.whichColor.toString else " "
//
//  val label =
//    new Label {
//      text = cellText(row, column)
//      font = new Font("Verdana", 1, 36)
//    }
//
//  val cell = new BoxPanel(Orientation.Vertical) {
//    contents += label
//    preferredSize = new Dimension(51, 51)
//    background = if (controller.isAvailable(row, column)) givenCellColor else cellColor
//    border = Swing.BeveledBorder(Swing.Raised)
//    listenTo(mouse.clicks)
//    listenTo(controller)
//    reactions += {
//      case e: CellChanged => {
//        label.text = cellText(row, column)
//        repaint
//      }
//      case MouseClicked(src, pt, mod, clicks, pops) => {
//        repaint
//      }
//    }
//  }
//
//  def redraw = {
//    contents.clear()
//    if ((controller.isShowCandidates(row, column) || controller.showAllCandidates) && !controller.isSet(row, column)) {
//      setBackground(candidates)
//      contents += candidates
//    } else {
//      label.text = cellText(row, column)
//      setBackground(cell)
//      contents += cell
//    }
//    repaint
//  }
//
//  def setBackground(p: Panel) = p.background = if (controller.isAvailable(row, column)) givenCellColor
}