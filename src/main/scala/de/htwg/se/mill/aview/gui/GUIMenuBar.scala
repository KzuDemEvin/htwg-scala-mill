package de.htwg.se.mill.aview.gui


import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller

import scala.swing.event.Key
import scala.swing.{Action, Menu, MenuBar, MenuItem, TextField}

class GUIMenuBar(controller: Controller) extends MenuBar {
  def menuBar:MenuBar = {
    val menuBar = new MenuBar {
      contents += new Menu("File") {
        mnemonic = Key.F
        contents += new MenuItem(Action("New") { controller.createEmptyField(controller.fieldsize) })
        contents += new MenuItem(Action("Random") { controller.createRandomField(controller.fieldsize) })
        contents += new MenuItem(Action("Quit") { System.exit(0) })
      }
      contents += new Menu("Edit") {
        mnemonic = Key.E
        contents += new MenuItem(Action("Undo") { controller.undo })
        contents += new MenuItem(Action("Redo") { controller.redo })
      }
    }
    menuBar
  }

}
