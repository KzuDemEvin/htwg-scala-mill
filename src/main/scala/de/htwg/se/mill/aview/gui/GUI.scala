package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.Controller

import scala.swing.event.Key
import scala.swing.{Action, BoxPanel, GridPanel, Label, MainFrame, Menu, MenuBar, MenuItem, Orientation}


class GUI(controller: Controller) extends MainFrame {
  title = "HTWG MILL"
  visible = true

  contents = new BoxPanel(Orientation.Vertical) {
    contents += new GUIGridPanel(controller).gridPanel
    contents += new GUIMenuBar(controller).menuBar
  }

}
