package de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Field => BaseField}

class Field @Inject() ( @Named("DefaultSize") size: Int) extends BaseField(size) {

  override def createNewGrid: FieldInterface = new Field(size)

}
