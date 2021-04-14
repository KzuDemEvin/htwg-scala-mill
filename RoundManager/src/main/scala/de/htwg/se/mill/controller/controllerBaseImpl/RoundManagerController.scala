package de.htwg.se.mill.controller.controllerBaseImpl

import com.google.gson.Gson
import de.htwg.se.mill.controller.RoundManagerControllerInterface
import de.htwg.se.mill.model.RoundManager

class RoundManagerController extends RoundManagerControllerInterface {

  var mgr: RoundManager = new RoundManager()

  override def blackTurn: String = new Gson().toJson(mgr.blackTurn())

  override def whiteTurn: String = new Gson().toJson(mgr.whiteTurn())

  override def getRound: String = new Gson().toJson(mgr.roundCounter)
}
