package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

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

  contents = createBoxPanel(label, inputtxt, createbtn1)

  reactions += {
    case ButtonClicked(component) if component == createbtn1 =>
      controller.createPlayer(inputtxt.text, 1)
      contents = nextPlayer()
    case ButtonClicked(component) if component == createbtn2 =>
      controller.createPlayer(inputtxt.text, 2)
      dispose()
  }

  visible = true
  centerOnScreen()

  def nextPlayer():BoxPanel = {
    label.text = "Name von Player2 eingeben: "
    inputtxt.text = ""
    createBoxPanel(label, inputtxt, createbtn2)
  }

  private def createBoxPanel(label: Label, inputtxt: TextField, createbtn: Button): BoxPanel = {
    new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel(Alignment.Center)(label)
      contents += new FlowPanel(Alignment.Center)(inputtxt)
      contents += new FlowPanel(Alignment.Center)(createbtn)
    }
  }
}
