package mmlTests.a201603a.scalaTest.gpt35turbo

import mmlTests.a201603a.scalaTest.Bag

import scala.collection.immutable.ListSet


class BagImpl[X](private val elements: List[X]) extends Bag[X] {

  override def numberOfCopies(x: X): Int = elements.count(_ == x)

  override def size: Int = elements.size

  override def toList: List[X] = ListSet(elements: _*).toList
}

