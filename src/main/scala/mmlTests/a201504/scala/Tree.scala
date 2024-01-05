package mmlTests.a201504.scala

trait Tree[X] {

  /**
   * @return the number of elements in the tree
   */
  def size: Int

  /**
   * @return the root element of the tree
   * @throws IllegalStateException if the tree is empty
   */
  def getRoot: X

  /**
   * @return the ordered list of sons (a defensive copy!)
   * @throws IllegalStateException if the tree is empty
   */
  def getSons: List[Tree[X]]

  /**
   * @return whether an element equal to x is contained somewhere in the tree
   */
  def contains(x: X): Boolean

  /**
   * @return the subtree (at any level of depth) having argument node as root, or null if none exists
   * @throws IllegalStateException if the tree is empty
   */
  def getSubTree(node: X): Tree[X]

  /**
   * @return the list of elements contained in the tree (in any order)
   */
  def toList: List[X]
}
