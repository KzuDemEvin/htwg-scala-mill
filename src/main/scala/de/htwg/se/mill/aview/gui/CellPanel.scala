package de.htwg.se.mill.aview.gui

import scala.swing._
import scala.swing.event._
import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface, FlyModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.{Cell, Color}
import javax.swing.ImageIcon

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {

  val unavailableColor = new Color(238, 238, 238) // backgroundcolor
  val transparentColor = new Color(0, 0, 0, 0)

  val sizeDim = new Dimension(100, 100)

  def myCell = controller.cell(row, column)

  val horizontalCells = List((0, 1), (0, 2), (0, 4), (0,5),
                                (1, 2),(1, 4),
                                (5, 2),(5, 4),
                        (6, 1), (6, 2), (6, 4), (6, 5))

  val verticalCells = List((1, 0),(1, 6),
               (2, 0),(2, 1),(2, 5),(2, 6),
               (4, 0),(4, 1),(4, 5),(4, 6),
                      (5, 0),(5, 6))

  // 0 = white, 1 = black, 2 = available, 3 = notValidHorizontal, 4 = notValidVertical, 5 = middle
  def cellType(row: Int, col: Int): Int = {
    var cellType = 5
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
      for (x <- horizontalCells) {
        if (x._1 == row && x._2 == col) {
          cellType = 3
        }
      }
      for (x <- verticalCells) {
        if (x._1 == row && x._2 == col) {
          cellType = 4
        }
      }
    }
    cellType
  }

  def cellIcon(row: Int, col: Int): ImageIcon = {
    cellType(row, col) match {
      case 0 => new ImageIcon("src\\assets\\media\\WhiteStone.png")
      case 1 => new ImageIcon("src\\assets\\media\\BlackStone.png")
      case 2 => new ImageIcon("src\\assets\\media\\CellStone.png")
      case 3 => new ImageIcon("src\\assets\\media\\UnavailableCellHorizontal.png")
      case 4 => new ImageIcon("src\\assets\\media\\UnavailableCellVertical.png")
      case 5 => new ImageIcon("src\\assets\\media\\MiddleCell.png")
    }
  }

  val setButton = new Button {
    minimumSize = sizeDim
    maximumSize = sizeDim
    preferredSize = sizeDim
    background = unavailableColor
    icon = cellIcon(row, column)
  }

  val notValidButton = new Button {
    minimumSize = sizeDim
    maximumSize = sizeDim
    preferredSize = sizeDim
    background = unavailableColor
    icon = cellIcon(row, column)
  }

  val cell = new BoxPanel(Orientation.Vertical) {
    if (cellType(row, column) < 3) {
      contents += setButton
    } else {
      contents += notValidButton
    }
    preferredSize = sizeDim

    listenTo(controller)
    listenTo(setButton)
    reactions += {
      case ButtonClicked(component) if component == setButton => {
        val whichCmd = controller.selectDriveCommand()
        whichCmd match {
          case SetModeState() =>
            if (controller.setCounter >= 1) {
              if (controller.removeStone(row, column)) {
                controller.setCounter = 0
              } else {
                controller.setCounter += 1
              }
            } else {
              controller.set(row, column)
              val m = controller.checkMill(row, column)
              m match {
                case "White Mill" => controller.setCounter += 1
                case "Black Mill" => controller.setCounter += 1
                case "No Mill" => controller.setCounter = 0
              }
            }
          case MoveModeState() => controller.moveCounter += 1
            print("movecounter:" + controller.moveCounter + "\n")
            if (controller.moveCounter == 2) {
              controller.moveStone(controller.tmpCell._1, controller.tmpCell._2, row, column)
              val m = controller.checkMill(row, column)
              m match {
                case "White Mill" => controller.moveCounter += 1
                case "Black Mill" => controller.moveCounter += 1
                case "No Mill" => controller.moveCounter = 0
              }
            } else if (controller.moveCounter >= 4) {
              if (controller.removeStone(row, column)) {
                controller.moveCounter = 0
              } else {
                controller.moveCounter += 1
              }
            } else {
              controller.tmpCell = (row, column)
            }
          case FlyModeState() => controller.flyCounter += 1
            print("flycounter:" + controller.flyCounter + "\n")
            if (controller.flyCounter == 2) {
              controller.fly(controller.tmpCell._1, controller.tmpCell._2, row, column)
              val m = controller.checkMill(row, column)
              m match {
                case "White Mill" => controller.flyCounter += 1
                case "Black Mill" => controller.flyCounter += 1
                case "No Mill" => controller.flyCounter = 0
              }
            } else if (controller.flyCounter >= 4) {
              if (controller.removeStone(row, column)) {
                controller.flyCounter = 0
              } else {
                controller.flyCounter += 1
              }
            } else {
              controller.tmpCell = (row, column)
            }
        }
      }
      case event:CellChanged =>
      repaint
    }
  }

  def redraw:Unit = {
    contents.clear()
    setButton.background = unavailableColor
    setButton.icon = cellIcon(row, column)
    notValidButton.background = unavailableColor
    notValidButton.icon = cellIcon(row, column)
    contents += cell
    repaint
  }
}