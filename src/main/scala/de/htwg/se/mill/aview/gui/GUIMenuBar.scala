package de.htwg.se.mill.aview.gui


import de.htwg.se.mill.controller.controllerComponent.ControllerInterface

import scala.swing.event.Key
import scala.swing.{Action, Menu, MenuBar, MenuItem}

class GUIMenuBar(controller: ControllerInterface) extends MenuBar {
  def menuBar:MenuBar = {
    val menuBar = new MenuBar {
      contents += new Menu("Options") {
        mnemonic = Key.O
        contents += new MenuItem(Action("New") { controller.createEmptyField(controller.fieldsize) })
        contents += new MenuItem(Action("Random") { controller.createRandomField(controller.fieldsize) })
        contents += new MenuItem(Action("Quit") { System.exit(0) })
      }
      contents += new Menu("File") {
        mnemonic = Key.F
        contents += new MenuItem(Action("Save") {controller.save})
        contents += new MenuItem(Action("Load") {controller.load})
        contents += new Menu("Change Savetype") {
          contents += new MenuItem(Action("Database") { controller.changeSaveMethod("db") })
          contents += new MenuItem(Action("File") { controller.changeSaveMethod("fileio") })
        }
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
