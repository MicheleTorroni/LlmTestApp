package llmTests.a201504.scala.gpt35turbo

import llmTests.a201504.scala.{Tree, TreeFactory}
import scala.collection.mutable.ListBuffer
import java.util


class TreeFactoryImpl[X] extends TreeFactory[X] {

  override def emptyTree(): Tree[X] = {
    new TreeImpl[X](null.asInstanceOf[X], List[Tree[X]]())
  }

  override def consTree(root: X, sons: util.List[Tree[X]]): Tree[X] = {
    val children: ListBuffer[Tree[X]] = ListBuffer[Tree[X]]()
    sons.forEach(child => children.addOne(child))
    new TreeImpl[X](root, children.toList)
  }
}