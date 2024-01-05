package mmlTests.a201706.scala.gpt4

import mmlTests.shared.scala.Pair
import mmlTests.a201706.scala.Sequence
import mmlTests.a201706.scala.SequenceHelpers

import java.util.concurrent.atomic.AtomicInteger
import java.util.function.BinaryOperator
import scala.jdk.CollectionConverters.*

class SequenceHelpersImpl extends SequenceHelpers {

  class SequenceImpl[T](getNext: () => T) extends Sequence[T] {
    override def nextElement(): T = getNext()
  }

  override def of[X](x: X): Sequence[X] = new SequenceImpl(() => x)

  override def cyclic[X](l: java.util.List[X]): Sequence[X] = {
    val iterator = Iterator.continually(l.asScala).flatten
    new SequenceImpl(() => iterator.next())
  }

  override def incrementing(start: Int, increment: Int): Sequence[Integer] = {
    val current = new AtomicInteger(start - increment)
    new SequenceImpl(() => current.addAndGet(increment))
  }

  override def accumulating[X](input: Sequence[X], op: BinaryOperator[X]): Sequence[X] = {
    var acc: X = input.nextElement()
    new SequenceImpl(() => {
      val next = input.nextElement()
      acc = op.apply(acc, next)
      acc
    })
  }

  def zip[X](input: Sequence[X]): Sequence[Pair[X, Integer]] = {
    val count = new AtomicInteger(0)
    new SequenceImpl(() => new Pair(input.nextElement(), count.getAndIncrement))
  }
}