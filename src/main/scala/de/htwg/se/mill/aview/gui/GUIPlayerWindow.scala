package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.ControllerInterface
import de.htwg.se.mill.model.playerComponent.Player

import scala.swing.FlowPanel.Alignment
import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Dimension, FlowPanel, Font, Label, MainFrame, Orientation, TextField}

class GUIPlayerWindow(controller:ControllerInterface) extends MainFrame {
  listenTo(controller)
  title = "Players"

  val label = new Label("Name von Player1 eingeben: ")
  label.font = Font("Dialog", Font.Bold, 20)
  val inputtxt = new TextField()
  inputtxt.preferredSize = new Dimension(200, 30)
  val createbtn1 = new Button("Create")
  val createbtn2 = new Button("Create")
  listenTo(createbtn1)
  listenTo(createbtn2)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel(Alignment.Center)(label)
    contents += new FlowPanel(Alignment.Center)(inputtxt)
    contents += new FlowPanel(Alignment.Center)(createbtn1)
  }

  reactions += {
    case ButtonClicked(component) if component == createbtn1 =>
      controller.getRoundManager.player1 = Player(inputtxt.text)
      contents = nextPlayer()
    case ButtonClicked(component) if component == createbtn2 =>
      controller.getRoundManager.player2 = Player(inputtxt.text)
      dispose()
  }

  visible = true
  centerOnScreen()

  def nextPlayer():BoxPanel = {
    label.text = "Name von Player2 eingeben: "
    inputtxt.text = ""
    val boxpanel = new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel(Alignment.Center)(label)
      contents += new FlowPanel(Alignment.Center)(inputtxt)
      contents += new FlowPanel(Alignment.Center)(createbtn2)
    }
    boxpanel
  }
}
