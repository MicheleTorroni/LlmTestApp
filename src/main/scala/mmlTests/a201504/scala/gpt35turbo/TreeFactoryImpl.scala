package mmlTests.a201504.scala.gpt35turbo

import mmlTests.a201504.scala.Tree
import mmlTests.a201504.scala.TreeFactory
import mmlTests.a201504.scala.gpt35turbo.TreeImpl

class TreeFactoryImpl[X] extends TreeFactory[X] {
  override def emptyTree(): Tree[X] = new TreeImpl[X](null.asInstanceOf[X], Nil)

  override def consTree(root: X, sons: List[Tree[X]]): Tree[X] = new TreeImpl[X](root, sons)
}