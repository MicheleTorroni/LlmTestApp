package llmTests.a201504.scala.gpt4

import llmTests.a201504.scala.{Tree, TreeFactory}

import java.util
import scala.jdk.CollectionConverters.*


class TreeFactoryImpl[X] extends TreeFactory[X] {
  override def emptyTree(): Tree[X] = new TreeImpl(None, List.empty)

  override def consTree(root: X, sons: util.List[Tree[X]]): Tree[X] =
    new TreeImpl(Option(root), Option(sons).map(_.asScala.toList).getOrElse(List.empty))
}