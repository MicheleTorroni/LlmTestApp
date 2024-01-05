package mmlTests.a201706.scala.gpt35turbo

import java.util
import java.util.function.BinaryOperator
import mmlTests.shared.scala.Pair
import mmlTests.a201706.scala.Sequence
import mmlTests.a201706.scala.SequenceHelpers

abstract class SequenceHelpersImpl extends SequenceHelpers {
  override def of[X](x: X): Sequence[X] = new Sequence[X] {
    override def nextElement(): X = x
  }

  override def cyclic[X](l: util.List[X]): Sequence[X] = new Sequence[X] {
    private var index = 0

    override def nextElement(): X = {
      val element = l.get(index)
      index = (index + 1) % l.size()
      element
    }
  }

  override def incrementing(start: Int, increment: Int): Sequence[Integer] = new Sequence[Integer] {
    private var value = start

    override def nextElement(): Integer = {
      val element = value
      value += increment
      element
    }
  }

  override def accumulating[X](input: Sequence[X], op: BinaryOperator[X]): Sequence[X] = new Sequence[X] {
    private var accumulator: Option[X] = None

    override def nextElement(): X = {
      val element = input.nextElement()
      accumulator = accumulator.map(acc => op.apply(acc, element)).orElse(Some(element))
      accumulator.get
    }
  }

//  override def zip[X](input: Sequence[X]): Sequence[Pair[X, Integer]] = new Sequence[Pair[X, Integer]] {
//    private var index = 0
//
//    override def nextElement(): Pair[X, Integer] = {
//      val element = input.nextElement()
//      val pair = new Pair[X, Integer](element, index)
//      index += 1
//      pair
//    }
//  }
}