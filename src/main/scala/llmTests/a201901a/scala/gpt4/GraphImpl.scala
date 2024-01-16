package llmTests.a201901a.scala.gpt4

import llmTests.a201901a.scala.{Graph, GraphFactory}

import java.util
import java.util.stream.Stream
import llmTests.shared.scala.Pair

class GraphImpl[X](private var _nodes: util.Set[X] = new util.HashSet[X](),
                   private var _edges: util.HashMap[X, util.HashSet[X]] = new util.HashMap[X, util.HashSet[X]]()) extends Graph[X] {

  def nodes: util.Set[X] = _nodes
  def nodes_=(value: util.Set[X]): Unit = _nodes = value

  def edges: util.HashMap[X, util.HashSet[X]] = _edges
  def edges_=(value: util.HashMap[X, util.HashSet[X]]): Unit = _edges = value

  override def getNodes: util.Set[X] = nodes

  override def edgePresent(start: X, end: X): Boolean = edges.getOrDefault(start, new util.HashSet[X]()).contains(end)

  override def getEdgesCount: Int = edges.values().stream().mapToInt(_.size()).sum()

  override def getEdgesStream: Stream[Pair[X, X]] =
    edges.entrySet().stream().flatMap { entry => entry.getValue.stream().map((v: X) => new Pair(entry.getKey, v))}
}