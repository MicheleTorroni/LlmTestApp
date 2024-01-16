package llmTests.a202003c.scala

import java.util
import java.util.{Map, Optional, Set}


/**
 * An interface modelling a table, with rows, columns and cells: essentially a set of triples
 * of a row, a column and a value in the cell.
 *
 * @param <R> the type of rows
 * @param <C> the type of columns
 * @param <V> the type of values in cells
 */trait Table[R, C, V] { /**
 * @return the set of all rows
 */def rows: util.Set[R]
  /**
   * @return the set of all columns
   */def columns: util.Set[C]
  /**
   * @return a map associating columns to row-value maps
   * THIS IS OPTIONAL IN THIS EXAM!
   */def asColumnMap: util.Map[C, util.Map[R, V]]
  /**
   * @return a map associating rows to column-value maps
   */def asRowMap: util.Map[R, util.Map[C, V]]
  /**
   * @param row
   * @param column
   * @return the value in that specific row and column, if it exists
   */def getValue(row: R, column: C): Optional[V]
  /**
   * @param row
   * @param column
   * @param value
   *
   * puts the value in that row-column cell
   *
   */def putValue(row: R, column: C, value: V): Unit
}
