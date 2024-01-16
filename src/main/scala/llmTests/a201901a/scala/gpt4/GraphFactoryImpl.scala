package llmTests.a201901a.scala.gpt4

import llmTests.a201901a.scala.{Graph, GraphFactory}
import llmTests.a201901a.scala.gpt4.GraphImpl

import java.util
import java.util.stream.Stream
import llmTests.shared.scala.Pair
import scala.jdk.CollectionConverters._

class GraphFactoryImpl extends GraphFactory {

  private def createEdges[X](nodes: Seq[X], isClosed: Boolean, isBiDirectional: Boolean): Set[Pair[X, X]] =
    (if (isClosed) nodes :+ nodes.head else nodes)
      .sliding(2)
      .flatMap{
        case Seq(from, to) =>
          if (isBiDirectional) Seq(Pair(from, to), Pair(to, from))
          else Seq(Pair(from, to))
      }
      .toSet

  override def createDirectedChain[X](nodes: util.List[X]): Graph[X] =
    new GraphImpl(createEdges(nodes.asScala.toList, isClosed = false, isBiDirectional = false))

  override def createBidirectionalChain[X](nodes: util.List[X]): Graph[X] =
    new GraphImpl(createEdges(nodes.asScala.toList, isClosed = false, isBiDirectional = true))

  override def createDirectedCircle[X](nodes: util.List[X]): Graph[X] =
    new GraphImpl(createEdges(nodes.asScala.toList, isClosed = true, isBiDirectional = false))

  override def createBidirectionalCircle[X](nodes: util.List[X]): Graph[X] =
    new GraphImpl(createEdges(nodes.asScala.toList, isClosed = true, isBiDirectional = true))

  override def createDirectedStar[X](center: X, nodes: util.Set[X]): Graph[X] =
    new GraphImpl(
      nodes.asScala.map(node => Pair(center, node)).toSet
    )

  override def createBidirectionalStar[X](center: X, nodes: util.Set[X]): Graph[X] =
    new GraphImpl(
      nodes.asScala.flatMap(node => Seq(Pair(center, node), Pair(node, center))).toSet
    )

  override def createFull[X](nodes: util.Set[X]): Graph[X] = {
    val nodesSeq = nodes.asScala.toSeq
    new GraphImpl(
      nodesSeq.flatMap(node => nodesSeq.map(other => Pair(node, other))).filter(pair => pair.fst != pair.snd).toSet
    )
  }

  override def combine[X](g1: Graph[X], g2: Graph[X]): Graph[X] =
    new GraphImpl(
      (g1.asInstanceOf[GraphImpl[X]].edges ++ g2.asInstanceOf[GraphImpl[X]].edges)
    )
}