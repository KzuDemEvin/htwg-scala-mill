package de.htwg.se.mill.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.model.fieldComponent.{Cell, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

import scala.xml.{Node, NodeSeq, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load: FieldInterface = {
    var field: FieldInterface = null
    val file = scala.xml.XML.loadFile("field.xml")
    val roundCounter = (file \\ "field" \ "@roundCounter").text.toInt
    val injector = Guice.createInjector(new MillModule)
    field = injector.instance[FieldInterface](Names.named("normal"))
    val cellNodes = (file \\ "cell")
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val content: String = cell.text.trim
      content match {
        case "white" => field = field.set(row, col, Cell("cw"))
        case "black" => field = field.set(row, col, Cell("cb"))
        case "noColor" => field = field.set(row, col, Cell("ce"))
      }
    }
    field.setRoundCounter(roundCounter)
    field
  }

  def save(field: FieldInterface): Unit = saveString(field)

  def saveXML(field: FieldInterface): Unit = {
    scala.xml.XML.save("field.xml", fieldToXml(field))
  }

  def saveString(field: FieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(fieldToXml(field))
    pw.write(xml)
    pw.close
  }

  def fieldToXml(field: FieldInterface): Node = {
    <field roundCounter={ field.getRoundCounter().toString }>
      {
      for {
        row <- 0 until field.size
        col <- 0 until field.size
      } yield cellToXml(field, row, col)
      }
    </field>
  }

  def cellToXml(field: FieldInterface, row: Int, col: Int): Node = {
    <cell row={ row.toString } col={ col.toString }>
      { field.cell(row, col).getContent.whichColor }
    </cell>
  }


}
