package mmlTests.a201504.scala.gpt4

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import mmlTests.a201504.scala.Tree


class TreeImpl(val root: String, val sons: Option[List[Tree[String]]]) extends Tree[String] {

  lazy val size: Int = if(sons.isDefined) 1 + sons.get.foldLeft(0)(_ + _.size) else 0

  def getRoot: String = if (size == 0) throw new IllegalStateException("Tree is empty!") else root

  def getSons: List[Tree[String]] = {
    if (size == 0)
      throw new IllegalStateException("Tree is empty!")
    sons.getOrElse(Nil)
  }

  def contains(x: String): Boolean =
    if (getSons.isEmpty) false
    else getRoot == x || getSons.exists(_.contains(x))

  def getSubTree(node: String): Tree[String] = {
    if (size == 0)
      throw new IllegalStateException("Tree is empty!")
    if (root == node) this
    else getSons.map(_.getSubTree(node)).find(_ != null).orNull
  }

  def toList: List[String] =
    if (size == 0) List()
    else root :: getSons.flatMap(_.toList)
}