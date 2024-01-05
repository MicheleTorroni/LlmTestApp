package mmlTests.a201504

import mmlTests.a201504.scala.{Tree, TreeFactory}
import mmlTests.a201504.scala.gpt4.TreeFactoryImpl
import org.junit.Assert.assertThrows
import org.junit.{Assert, Test}
import org.junit.Assert._

class Test2015a04Scala {
  @Test
  def testBasic(): Unit = {
    val tf: TreeFactory[String] = new TreeFactoryImpl
    val son1: Tree[String] = tf.consTree("a", List(
      tf.consTree("aa", Nil), tf.consTree("ab", Nil), tf.consTree("ac", Nil)
    ))
    val son2: Tree[String] = tf.consTree("b", Nil)
    val son3: Tree[String] = tf.consTree("c", List(
      tf.consTree("ca", Nil),
      tf.consTree("cb", Nil),
      tf.consTree("cc", List(
        tf.consTree("cca", Nil),
        tf.consTree("ccb", Nil)
      ))
    ))
    val tree: Tree[String] = tf.consTree("root", List(son1, son2, son3))

    assert(son1.size == 4)
    assert(son2.size == 1)
    assert(son3.size == 6)
    assert(tree.size == 12)

    assert(son1.getRoot == "a")
    assert(son2.getRoot == "b")
    assert(son3.getRoot == "c")
    assert(tree.getRoot == "root")

    assert(tree.contains("a"))
    assert(tree.contains("b"))
    assert(tree.contains("c"))
    assert(tree.contains("ccb"))
    assert(!tree.contains("ccc"))

    assert(son1.toList.toSet == Set("a", "aa", "ab", "ac"))
    assert(son2.toList.toSet == Set("b"))
    assert(son3.toList.toSet == Set("c", "ca", "cb", "cc", "cca", "ccb"))
    assert(tree.toList.toSet == Set("c", "ca", "cb", "cc", "cca", "ccb", "b", "a", "aa", "ab", "ac"))

    assert(tree.getSons(0).toList.toSet == Set("a", "aa", "ab", "ac"))
    assert(tree.getSons(1).toList.toSet == Set("b"))
    assert(tree.getSons(2).toList.toSet == Set("c", "ca", "cb", "cc", "cca", "ccb"))

    assert(tree.getSubTree("a").toList.toSet == Set("a", "aa", "ab", "ac"))
    assert(tree.getSubTree("b").toList.toSet == Set("b"))
    assert(tree.getSubTree("c").toList.toSet == Set("c", "ca", "cb", "cc", "cca", "ccb"))
    assert(tree.getSubTree("root").toList.toSet == Set("c", "ca", "cb", "cc", "cca", "ccb", "b", "a", "aa", "ab", "ac"))
    assert(tree.getSubTree("cc").toList.toSet == Set("cc", "cca", "ccb"))
  }

  @Test
  def optionalTestEmpty(): Unit = {
    val tf: TreeFactory[String] = new TreeFactoryImpl
    val tree: Tree[String] = tf.emptyTree()

    assert(tree.size == 0)

    try {
      tree.getSons
      fail("can't get sons if emty")
    } catch {
      case e: IllegalStateException =>

      case e: Exception =>
        fail("wrong exception")
    }

    // non si può accedere a subtrees
    try {
      tree.getSubTree("a")
      fail("can't get subtrees if emty")
    } catch {
      case e: IllegalStateException =>

      case e: Exception =>
        fail("wrong exception")
    }
  }
}
