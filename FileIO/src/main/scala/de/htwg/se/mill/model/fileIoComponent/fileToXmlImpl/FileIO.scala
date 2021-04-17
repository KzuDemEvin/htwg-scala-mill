package de.htwg.se.mill.model.fileIoComponent.fileToXmlImpl

import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOInterface {

  // TODO
  override def load(filename: Option[String] = Some("field.xml")): String = {
    val file = scala.xml.XML.loadFile(filename match {
      case Some(fn) => fn
      case None => "field.xml"
    })
    file.toString()
    /*
    val roundCounter = (file \\ "field" \ "@roundCounter").text.toInt
    val player1Mode = (file \\ "field" \ "@player1Mode").text
    val player2Mode = (file \\ "field" \ "@player2Mode").text
    val injector = Guice.createInjector(new MillModule)
    var field = injector.instance[FieldInterface](Names.named("normal"))
    val cellNodes = file \\ "cell"
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val content: String = cell.text.trim
      field = content match {
        case "white" => field.set(row, col, Cell("cw"))
        case "black" => field.set(row, col, Cell("cb"))
        case "noColor" => field.set(row, col, Cell("ce"))
      }
    }
    field.setRoundCounter(roundCounter)
      .setPlayer1Mode(player1Mode)
      .setPlayer2Mode(player2Mode)*/
  }

  override def save(field: FieldInterface, filename: Option[String] = Some("field.xml")): Unit = {
    saveString(field, filename match {
      case Some(fn) => fn
      case None => "field.xml"
    })
  }

  def saveXML(field: FieldInterface, filename: String = "field.xml"): Unit = {
    scala.xml.XML.save(filename = filename, node = fieldToXml(field), enc = "UTF-8", xmlDecl = true)
  }

  def saveString(field: FieldInterface, filename: String): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(filename))
    val xml = new PrettyPrinter(120, 4).format(fieldToXml(field))
    pw.write(xml)
    pw.close()
  }

  def fieldToXml(field: FieldInterface): Elem = {
    <field roundCounter={field.savedRoundCounter.toString} player1Mode={field.player1Mode}
           player2Mode={field.player2Mode}>
      {for {
      row <- 0 until field.size
      col <- 0 until field.size
    } yield cellToXml(field, row, col)}
    </field>
  }

  def cellToXml(field: FieldInterface, row: Int, col: Int): Elem = {
    <cell row={row.toString} col={col.toString}>
      {field.cell(row, col).content.color}
    </cell>
  }


}
