package llmTests.a201901a.scala.gpt4

import llmTests.a201901a.scala.{Graph, GraphFactory}

import java.util
import java.util.stream.Stream
import llmTests.shared.scala.Pair
import scala.jdk.CollectionConverters._

class GraphImpl[X](val edges: Set[Pair[X, X]]) extends Graph[X] {
  override def getNodes: util.Set[X] =
    edges.flatMap(edge => Seq(edge.fst, edge.snd)).asJava

  override def edgePresent(start: X, end: X): Boolean =
    edges.contains(Pair(start, end))

  override def getEdgesCount: Int =
    edges.size

  override def getEdgesStream: Stream[Pair[X, X]] =
    edges.toSeq.asJava.stream()
}