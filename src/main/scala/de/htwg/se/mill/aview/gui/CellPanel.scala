package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {

  var cellType: Int = 5

  val unavailableColor = new Color(238, 238, 238) // backgroundcolor

  val sizeDim = new Dimension(100, 100)

  //upperLeft, upperRight, bottomRight, bottomLeft, Middle, HoriTop, HoriBottom, SideLeft, SideRight
  val imagesPerPosition = Map(List((0, 0), (1, 1), (2, 2)) -> new ImageIcon("src/assets/media/AvailableCellTopLeft.png"),
    List((0, 6), (1, 5), (2, 4)) -> new ImageIcon("src/assets/media/AvailableCellTopRight.png"),
    List((6, 6), (5, 5), (4, 4)) -> new ImageIcon("src/assets/media/AvailableCellBottomRight.png"),
    List((6, 0), (5, 1), (4, 2)) -> new ImageIcon("src/assets/media/AvailableCellBottomLeft.png"),
    List((1, 3), (3, 5), (5, 3), (3, 1)) -> new ImageIcon("src/assets/media/AvailableCellMiddle.png"),
    List((0, 3), (4, 3)) -> new ImageIcon("src/assets/media/AvailableCellHorizontalTop.png"),
    List((2, 3), (6, 3)) -> new ImageIcon("src/assets/media/AvailableCellHorizontalBottom.png"),
    List((3, 0), (3, 4)) -> new ImageIcon("src/assets/media/AvailableCellVerticalLeft.png"),
    List((3, 2), (3, 6)) -> new ImageIcon("src/assets/media/AvailableCellVerticalRight.png"))

  val horizontalCells = List((0, 1), (0, 2), (0, 4), (0, 5),
    (1, 2), (1, 4),
    (5, 2), (5, 4),
    (6, 1), (6, 2), (6, 4), (6, 5))

  val verticalCells = List((1, 0), (1, 6),
    (2, 0), (2, 1), (2, 5), (2, 6),
    (4, 0), (4, 1), (4, 5), (4, 6),
    (5, 0), (5, 6))

  // 0 = white, 1 = black, 2 = availableCell, 3 = notValidHorizontal, 4 = notValidVertical, 5 = middle
  def cellType(row: Int, col: Int): Unit = {
    if (controller.possiblePosition(row, column)) {
      controller.isSet(row, col)({
        case Some(isSet) =>
          val isSetBool = isSet.toBoolean
          if (isSetBool) {
            controller.color(row, col)({
              case Some(color) =>
                cellType = color.toInt
                redraw(false)
            })
          } else {
            cellType = 2
            redraw(false)
          }
        case None => cellType = 2
      })
    } else {
      if (horizontalCells.contains((row, col))) {
        cellType = 3
      } else {
        cellType = 4
      }
    }
    redraw(false)
  }

  def cellIcon(row: Int, col: Int): ImageIcon = {
    cellType match {
      case 0 => new ImageIcon("src/assets/media/WhiteStone.png")
      case 1 => new ImageIcon("src/assets/media/BlackStone.png")
      case 2 => cellIcon2(row, col)
      case 3 => new ImageIcon("src/assets/media/UnavailableCellHorizontal.png")
      case 4 => new ImageIcon("src/assets/media/UnavailableCellVertical.png")
      case 5 => new ImageIcon("src/assets/media/MiddleCell.png")
    }
  }

  def cellIcon2(row: Int, col: Int): ImageIcon = {
    var icon = new ImageIcon()
    for (x <- imagesPerPosition.keySet) {
      if (x.contains((row, col))) {
        icon = imagesPerPosition(x)
      }
    }
    icon
  }

  val setButton: Button = new Button {
    layoutButton(this)
  }

  val notValidButton: Button = new Button {
    layoutButton(this)
  }

  def layoutButton(btn: Button): Unit = {
    btn.minimumSize = sizeDim
    btn.maximumSize = sizeDim
    btn.preferredSize = sizeDim
    btn.background = unavailableColor
    btn.icon = cellIcon(row, column)
  }

  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += setButton
    preferredSize = sizeDim

    listenTo(controller)
    listenTo(setButton)
    reactions += {
      case ButtonClicked(_) =>
        controller.handleClick(row, column)({
          case Some(_) => redraw()
        })
        controller.getWinner({ case Some(winner) => if (winner.toInt != 0) winnerDialog() })
    }
  }

  def winnerDialog(): MainFrame = {
    val winnerframe: MainFrame = new MainFrame() {
      title = "Winner"
      val newGame = new Button("New Game")
      val exitGame = new Button("Exit Game")
      listenTo(newGame)
      listenTo(exitGame)
      controller.getWinnerText({ case Some(winnerText) =>
        contents = new BoxPanel(Orientation.Vertical) {
          val label = new Label(winnerText)
          label.font = Font("Impact", Font.Bold, 30)
          contents += new FlowPanel(label)
          contents += new FlowPanel(newGame, exitGame)
        }
      })
      reactions += {
        case ButtonClicked(component) if component == newGame => controller.createEmptyField(7)
          dispose()
        case ButtonClicked(component) if component == exitGame => System.exit(0)
      }
      size = new Dimension(400, 200)
      visible = true
      centerOnScreen()
    }
    winnerframe
  }


  def redraw(getCellType: Boolean = true): Unit = {
    contents.clear()
    if (getCellType) {
      cellType(row, column)
    }
    setButton.background = unavailableColor
    setButton.icon = cellIcon(row, column)
    notValidButton.background = unavailableColor
    notValidButton.icon = cellIcon(row, column)
    contents += cell
    repaint
  }
}