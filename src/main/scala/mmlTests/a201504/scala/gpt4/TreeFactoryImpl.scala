package mmlTests.a201504.scala.gpt4

import mmlTests.a201504.scala.Tree
import mmlTests.a201504.scala.TreeFactory
import mmlTests.a201504.scala.gpt4.TreeImpl


class TreeFactoryImpl extends TreeFactory[String] {

  override def consTree(root: String, sons: List[Tree[String]]): TreeImpl = {
    new TreeImpl(root, Option(sons))
  }

  override def emptyTree(): Tree[String] = new TreeImpl("", None)
}