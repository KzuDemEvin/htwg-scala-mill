package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

case class Matrix[T](allRows: Vector[Vector[T]]) {
  def this(size: Int, filling: T) = this(Vector.tabulate(size, size) { (_, _) => filling })

  val allowedPosition = List((0, 0), (0, 3), (0, 6), (1, 1), (1, 3), (1, 5), (2, 2), (2, 3), (2, 4), (3, 0), (3, 1), (3, 2),
    (3, 4), (3, 5), (3, 6), (4, 2), (4, 3), (4, 4), (5, 1), (5, 3), (5, 5), (6, 0), (6, 3), (6, 6))

  val size: Int = allRows.size

  def cellsWithIndex(): Vector[((Int, Int), T)] = {
    for {
      (row, rowIndex) <- allRows.zipWithIndex
      (cell, colIndex) <- row.zipWithIndex
    } yield ((rowIndex, colIndex), cell)
  }

  def cell(row: Int, col: Int): T = allRows(row)(col)

  def allowedCell(row: Int, col: Int): Boolean = allowedPosition.contains((row, col))

  @deprecated
  def refill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size) { (_, _) => filling })

  def replaceCell(x1: Int, x2: Int, cell: T): Matrix[T] = copy(allRows.updated(x1, allRows(x1).updated(x2, cell)))
}
