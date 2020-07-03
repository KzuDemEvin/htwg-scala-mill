package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class CellPanelSpec extends WordSpec with Matchers {
  "A CellPanel" when {
    val controller = new Controller(new Field(7))
    "created" should {
      val availableCellPanel = new CellPanel(0, 0, controller)
      "have the cellType 'available'" in {
        availableCellPanel.cellType(0, 0) should be (2)
      }
      "have a cell isSet set to false" in {
        availableCellPanel.myCell.isSet should be(false)
      }
    }
    "placed a white stone within it" should {
      controller.set(0, 3)
      val blackCellPanel = new CellPanel(0, 3, controller)
      "have the cellType 'available'" in {
        blackCellPanel.cellType(0, 3) should be(0)
      }
      "have a cell isSet set to false" in {
        blackCellPanel.myCell.isSet should be(true)
      }
    }
    "placed a black stone within it" should {
      controller.set(0, 6)
      val whiteCellPanel = new CellPanel(0, 6, controller)
      "have the cellType 'available'" in {
        whiteCellPanel.cellType(0, 6) should be (1)
      }
      "have a cell isSet set to false" in {
        whiteCellPanel.myCell.isSet should be(true)
      }
    }
    "unavailable" should {
      val unavailableCellType = new CellPanel(0, 1, controller)
      "have the cellType 'unavailable'" in {
        unavailableCellType.cellType(0, 1) should be (3)
      }
      "have a cell isSet set to false" in {
        unavailableCellType.myCell.isSet should be(false)
      }
    }
  }

}
