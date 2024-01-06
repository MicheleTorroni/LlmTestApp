package mmlTests.a202003c.scala

import java.util
import java.util.function.BiFunction
import mmlTests.shared.java.Pair

import java.util.{Map, Set}

/**
 * An interface modelling a factory for various kinds of tables
 *
 */
/**
 * @author mirko
 *
 */trait TableFactory { /**
 * @param <R>
 * @param <C>
 * @param <V>
 * @param map
 * @return a table from a map associating each row-column pair to a value 
 */def fromMap[R, C, V](map: util.Map[Pair[R, C], V]): Table[R, C, V]
  /**
   * @param <R>
   * @param <C>
   * @param <V>
   * @param rows
   * @param columns
   * @param valueFunction
   * @return a table with given rows and columns, and with a function mapping rows and columns to values
   */def fromFunction[R, C, V](rows: util.Set[R], columns: util.Set[C], valueFunction: BiFunction[R, C, V]): Table[R, C, V]
  /**
   * @param <G>, the type of nodes of the graph, used both for row and columns
   * @param edges, a set of pair of edges (arcs from node to node)
   * @return a table representing a graph, which is a particular kind of table where rows and columns are the nodes,
   * and we have boolean true in all the row-column cells where there's an edge from the row's to the column's node  
   */def graph[G](edges: util.Set[Pair[G, G]]): Table[G, G, Boolean]
  /**
   * @param <V>, the type of elements in the matrix, used for values in the table 
   * @param values, a square matrix
   * @return a table representing a square matrix, which is a particular kind of table where rows and columns are integer indexes,
   * and values are of the element type of the matrix
   * THIS IS OPTIONAL IN THIS EXAM  
   */def squareMatrix[V](values: Array[Array[V]]): Table[Integer, Integer, V]
}
