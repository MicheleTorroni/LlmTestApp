package mmlTests.a201901a.scala.gpt4

import mmlTests.a201901a.scala.{Graph, GraphFactory}
import mmlTests.a201901a.scala.gpt4.GraphImpl

import java.util
import java.util.stream.Stream
import mmlTests.shared.scala.Pair

class GraphFactoryImpl() extends GraphFactory {

  private def createChain[X](nodes: util.List[X], bidirectional: Boolean): Graph[X] = {
    val g = new GraphImpl[X]()
    val it = nodes.iterator()
    if (it.hasNext) {
      var previous = it.next()
      g.nodes.add(previous)

      while (it.hasNext) {
        val current = it.next()
        g.nodes.add(current)
        g.edges.putIfAbsent(previous, new util.HashSet[X]())
        g.edges.get(previous).add(current)

        if (bidirectional) {
          g.edges.putIfAbsent(current, new util.HashSet[X]())
          g.edges.get(current).add(previous)
        }

        previous = current
      }
    }
    g
  }

  private def createCircle[X](nodes: util.List[X], bidirectional: Boolean): Graph[X] = {
    if (nodes.isEmpty) return new GraphImpl[X]()
    val g = createChain(nodes, bidirectional)
    val first = nodes.get(0)
    val last = nodes.get(nodes.size() - 1)

//    g.edges.putIfAbsent(last, new util.HashSet[X])
//    g.edges.get(last).add(first)
//    if (bidirectional) {
//      g.edges.putIfAbsent(first, new util.HashSet[X])
//      g.edges.get(first).add(last)
//    }

    g
  }

  private def createStar[X](center: X, nodes: util.Set[X], bidirectional: Boolean): Graph[X] = {
    val g = new GraphImpl[X]()
    g.nodes.add(center)
    g.nodes.addAll(nodes)
    g.edges.putIfAbsent(center, new util.HashSet[X])
    nodes.forEach { node =>
      g.edges.get(center).add(node)
      if (bidirectional) {
        g.edges.putIfAbsent(node, new util.HashSet[X])
        g.edges.get(node).add(center)
      }
    }
    g
  }

  override def createDirectedChain[X](nodes: util.List[X]): Graph[X] = createChain(nodes, bidirectional = false)

  override def createBidirectionalChain[X](nodes: util.List[X]): Graph[X] = createChain(nodes, bidirectional = true)

  override def createDirectedCircle[X](nodes: util.List[X]): Graph[X] = createCircle(nodes, bidirectional = false)

  override def createBidirectionalCircle[X](nodes: util.List[X]): Graph[X] = createCircle(nodes, bidirectional = true)

  override def createDirectedStar[X](center: X, nodes: util.Set[X]): Graph[X] = createStar(center, nodes, bidirectional = false)

  override def createBidirectionalStar[X](center: X, nodes: util.Set[X]): Graph[X] = createStar(center, nodes, bidirectional = true)

  override def createFull[X](nodes: util.Set[X]): Graph[X] = {
    val g = new GraphImpl[X]()
    g.nodes.addAll(nodes)
    nodes.forEach { node1 =>
      g.edges.putIfAbsent(node1, new util.HashSet[X])
      nodes.forEach { node2 =>
        if (!node1.equals(node2)) g.edges.get(node1).add(node2)
      }
    }
    g
  }

  def combine[X](g1: Graph[X], g2: Graph[X]): Graph[X] = {
    val nodes = new util.HashSet[X]()
    nodes.addAll(g1.getNodes)
    nodes.addAll(g2.getNodes)

    val edges = new util.HashMap[X, util.HashSet[X]]
//    g1.edges.forEach((k, v) => edges.put(k, new util.HashSet[X](v)))
//    g2.edges.forEach((k, v) => edges.get(k).addAll(v))

    new GraphImpl[X](nodes, edges)
  }
}