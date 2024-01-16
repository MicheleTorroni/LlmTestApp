package llmTests.a201504.scala.gpt35turbo

import llmTests.a201504.scala.{Tree, TreeFactory}
import scala.collection.mutable.ListBuffer
import java.util

class TreeImpl[X](val root: X, val children: List[Tree[X]]) extends Tree[X] {

  override def size: Int = {
    children.foldLeft(1)((acc, child) => acc + child.size)
  }

  override def getRoot: X = root

  override def getSons: util.List[Tree[X]] = {
    val sonList: util.List[Tree[X]] = new util.ArrayList[Tree[X]]()
    children.foreach(child => sonList.add(child))
    sonList
  }

  override def contains(x: X): Boolean = {
    if (root == x) {
      true
    } else {
      children.exists(child => child.contains(x))
    }
  }

  override def getSubTree(node: X): Tree[X] = {
    if (root == node) {
      this
    } else {
      val subTreeList: util.List[Tree[X]] = new util.ArrayList[Tree[X]]()
      children.foreach(child => subTreeList.add(child.getSubTree(node)))
      subTreeList.stream().filter(t => t != null).findFirst().orElse(null)
    }
  }

  override def toList: util.List[X] = {
    val elementList: util.List[X] = new util.ArrayList[X]()
    elementList.add(root)
    children.foreach(child => elementList.addAll(child.toList))
    elementList
  }
}