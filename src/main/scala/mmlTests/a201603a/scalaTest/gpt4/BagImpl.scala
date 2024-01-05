package mmlTests.a201603a.scalaTest.gpt4

import mmlTests.a201603a.scalaTest.{Bag, BagFactory}
import java.util
import java.util.function.ToIntFunction
import java.util.function.UnaryOperator

abstract class BagImpl[X](items: List[X]) extends Bag[X] {
  private val itemMap: Map[X, Int] = items.groupBy(identity).view.mapValues(_.size).toMap

  override def numberOfCopies(x: X): Int = itemMap.getOrElse(x, 0)

  override def size: Int = items.size

  //override def toList: List[X] = util.Arrays.asList(items: _*)
}
