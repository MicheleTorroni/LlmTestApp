package mmlTests.a201504.scala

import mmlTests.a201504.scala.Tree

trait TreeFactory[X] {

  /**
   * @return an empty tree (that is, with 0 elements)
   */
  def emptyTree(): Tree[X]

  /**
   * @return a tree with given root and list of sons
   * Note that it is not needed to check that all sons have different elements
   * sons can be null, meaning an empty list of sons
   */
  def consTree(root: X, sons: List[Tree[X]]): Tree[X]
}