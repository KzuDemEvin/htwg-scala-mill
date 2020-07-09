package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.{CellChanged, ControllerInterface}

import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, Button, Dimension, GridPanel, Label, MainFrame, Point, PopupMenu, TextField}


class GUI(controller: ControllerInterface) extends MainFrame {
  listenTo(controller)

  title = "Mill"
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.fieldsize, controller.fieldsize)


  menuBar = new GUIMenuBar(controller).menuBar

  val gridPanel: GridPanel = new GUIGridPanel(controller, cells).gridPanel
  val statusline: TextField = new TextField(controller.statusText, 100) { editable = false }
  val millline: TextField = new TextField(controller.millText, 80) { editable = false }
  val roundCounter: TextField = new TextField(controller.getRoundCounter.toString, 20) { editable = false}

  val topBar: BorderPanel = new BorderPanel {
   add(millline, BorderPanel.Position.West)
   add(roundCounter, BorderPanel.Position.East)
  }

  // ----------------------
  val button = new Button("POPUP")
  val newGame = new Button("New Game")
  val exitGame = new Button("Exit Game")
  listenTo(button)
  listenTo(newGame)
  listenTo(exitGame)
  // ----------------------

  contents = new BorderPanel {
    //add(millline, BorderPanel.Position.North)
    //add(roundCounter, BorderPanel.Position.East)
    add(topBar, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
    add(button, BorderPanel.Position.South)
  }

  visible = true

  val sizeDim = new Dimension(740, 840)
  size = sizeDim
  centerOnScreen()
  updateField()


  reactions += {
    case ButtonClicked(component) if component == button => popupWinnerMenu().show(button, -100, -100)
    case ButtonClicked(component) if component == newGame => controller.createEmptyField(7)
    case ButtonClicked(component) if component == exitGame => System.exit(0)
    case _: CellChanged => updateField()
  }

  def updateField(): Unit = {
    for {
      row <-0 until controller.fieldsize
      col <- 0 until controller.fieldsize
    } cells(row)(col).redraw()
    statusline.text = controller.statusText
    millline.text = controller.millText
    roundCounter.text = "Roundcounter: " + controller.getRoundCounter.toString
    if (controller.checkWinner() != 0) {
      popupWinnerMenu().show(topBar, 200, 200)
    }
    repaint
  }

  def popupWinnerMenu(): PopupMenu = {
    val popupMenu = new PopupMenu {
      contents += new Label(controller.winnerText)
      //contents += new TextField(controller.winnerText, 50)
      contents += newGame
      contents += exitGame
      preferredSize = new Dimension(100, 100)
    }
    popupMenu
  }

}
