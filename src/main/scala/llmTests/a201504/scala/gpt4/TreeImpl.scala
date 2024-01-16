package llmTests.a201504.scala.gpt4


import llmTests.a201504.scala.{Tree, TreeFactory}
import scala.jdk.CollectionConverters._
import java.util


class TreeImpl[X](root: Option[X], sons: List[Tree[X]]) extends Tree[X] {
  override def size: Int = root.map(_ => sons.foldLeft(1)((acc, son) => acc + son.size)).getOrElse(0)

  override def getRoot: X = root.getOrElse(throw new IllegalStateException("Tree is empty"))

  override def getSons: util.List[Tree[X]] = {
    if (root.isEmpty) throw new IllegalStateException("Tree is empty")
    sons.asJava
  }

  override def contains(x: X): Boolean = root.contains(x) || sons.exists(_.contains(x))

  override def getSubTree(node: X): Tree[X] = {
    if (root.isEmpty) throw new IllegalStateException("Tree is empty")
    else if (root.contains(node)) this
    else sons.flatMap(son => Option(son.getSubTree(node))).headOption.orNull
  }

  override def toList: util.List[X] = {
    if (root.isEmpty) new java.util.ArrayList[X]()
    else (root.toList ++ sons.flatMap(_.toList.asScala)).asJava
  }
}
