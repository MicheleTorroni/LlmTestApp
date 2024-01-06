package mmlTests.a201901a.scala


import java.util
import java.util.stream.Stream
import mmlTests.shared.scala.Pair

import java.util.Set


/**
 * An interface for modelling a directed graph
 *
 * @param X , the type of labels, namely, nodes of the graph
 */
trait Graph[X] {
  /**
   * @return the set of nodes
   */
  def getNodes: util.Set[X]

  /**
   * @param start
   * @param end
   * @return whether there's a direct edge from start to stop
   */
  def edgePresent(start: X, end: X): Boolean

  /**
   * @return the number of edges
   */
  def getEdgesCount: Int

  /**
   * @return a stream over all edges, modelled as pairs.
   *         IT IS IN THE OPTIONAL PART OF THE EXAM!
   */
  def getEdgesStream: Stream[Pair[X, X]]
}

