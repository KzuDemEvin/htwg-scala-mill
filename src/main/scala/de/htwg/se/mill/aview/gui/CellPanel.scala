package de.htwg.se.mill.aview.gui

import scala.swing._
import javax.swing.table._
import scala.swing.event._
import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.controller.CellChanged

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {

  val givenCellColor = new Color(200, 200, 255)
  val cellColor = new Color(224, 224, 255)
  val highlightedCellColor = new Color(192, 255, 192)

  def myCell = controller.cell(row, column)

  //def cellText(row: Int, col: Int) = if (controller.isSet(row, col)) "filled" + controller.cell(row, col).content.whichColor.toString else "leer"
  def cellText(row: Int, col: Int) = "besetzt"

  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 20)
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(100, 100)
    background = highlightedCellColor
    //background = if (controller.available(row, column)) givenCellColor else cellColor
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
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
      //setBackground(cell)
    contents += cell
    repaint
  }

//  def setBackground(p: Panel) = p.background = if (controller.isAvailable(row, column)) givenCellColor
}