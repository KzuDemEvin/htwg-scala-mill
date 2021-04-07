package de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.model.fieldComponent.{Cell, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load(filename: Option[String] = Some("field.json")): FieldInterface = {
    val sourceFile = Source.fromFile(filename match {
      case Some(fn) => fn
      case None => "field.json"
    })
    val source: String = sourceFile.getLines.mkString
    sourceFile.close()
    val json: JsValue = Json.parse(source)
    val roundCounter = (json \ "field" \ "roundCounter").get.toString.toInt
    val player1Mode = (json \ "field" \ "player1Mode").get.toString.replaceAll("\"", "")
    val player2Mode = (json \ "field" \ "player2Mode").get.toString.replaceAll("\"", "")
    val injector = Guice.createInjector(new MillModule)
    var field = injector.instance[FieldInterface](Names.named("normal"))
    for (index <- 0 until field.size * field.size) {
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      val color = (json \\ "color")(index).toString().replaceAll("\"", "")
      field = color match {
        case "white" => field.set(row, col, Cell("cw"))
        case "black" => field.set(row, col, Cell("cb"))
        case "noColor" => field.set(row, col, Cell("ce"))
      }
    }
    field.setRoundCounter(roundCounter)
      .setPlayer1Mode(player1Mode)
      .setPlayer2Mode(player2Mode)
  }

  override def save(field: FieldInterface, filename: Option[String] = Some("field.json")): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(filename match {
      case Some(fn) => fn
      case None => "field.json"
    }))
    pw.write(Json.prettyPrint(fieldToJson(field)))
    pw.close()
  }

  def fieldToJson(field: FieldInterface): JsValue = {
    Json.obj(
      "field" -> Json.obj(
        "roundCounter" -> JsNumber(field.savedRoundCounter),
        "player1Mode" -> JsString(field.player1Mode),
        "player2Mode" -> JsString(field.player2Mode),
        "cells" -> Json.toJson(
          for {
            row <- 0 until field.size
            col <- 0 until field.size
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "color" -> Json.toJson(field.cell(row, col).content.color)
            )
          }
        )
      )
    )
  }
}
