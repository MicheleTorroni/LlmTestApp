package mmlTests.a201504.scala.gpt35turbo

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import mmlTests.a201504.scala.Tree

class TreeImpl[X](val root: X, val sons: List[Tree[X]]) extends Tree[X] {
  override def size: Int = {
    @tailrec
    def sizeHelper(remaining: List[Tree[X]], acc: Int): Int = {
      remaining match {
        case Nil => acc
        case head :: tail => sizeHelper(tail, acc + head.size)
      }
    }

    sizeHelper(sons, 1)
  }

  override def getRoot: X = root

  override def getSons: List[Tree[X]] = sons

  override def contains(x: X): Boolean = {
    @tailrec
    def containsHelper(remaining: List[Tree[X]]): Boolean = {
      remaining match {
        case Nil => false
        case head :: tail =>
          if (head.getRoot == x || head.contains(x)) true
          else containsHelper(tail)
      }
    }

    containsHelper(sons)
  }

  override def getSubTree(node: X): Tree[X] = {
    @tailrec
    def getSubTreeHelper(remaining: List[Tree[X]]): Tree[X] = {
      remaining match {
        case Nil => null
        case head :: tail =>
          if (head.getRoot == node) head
          else {
            val subTree = head.getSubTree(node)
            if (subTree != null) return subTree
            getSubTreeHelper(tail)
          }
      }
    }

    getSubTreeHelper(sons)
  }

  override def toList: List[X] = {
    @tailrec
    def toListHelper(remaining: List[Tree[X]], acc: List[X]): List[X] = {
      remaining match {
        case Nil => acc
        case head :: tail => toListHelper(tail, acc ::: head.toList)
      }
    }

    root :: toListHelper(sons, Nil)
  }
}