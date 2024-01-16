package llmTests.a201603a.scala.gpt4

import llmTests.a201603a.scala.{Bag, BagFactory}
import java.util
import java.util.function.ToIntFunction
import java.util.function.UnaryOperator

abstract class BagImpl[X](items: List[X]) extends Bag[X] {
  private val itemMap: Map[X, Int] = items.groupBy(identity).view.mapValues(_.size).toMap

  override def numberOfCopies(x: X): Int = itemMap.getOrElse(x, 0)

  override def size: Int = items.size

  //override def toList: List[X] = util.Arrays.asList(items: _*)
}
