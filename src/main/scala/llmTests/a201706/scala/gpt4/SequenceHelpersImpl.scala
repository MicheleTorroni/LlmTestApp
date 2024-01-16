package llmTests.a201706.scala.gpt4

import llmTests.a201706.scala.Sequence
import llmTests.a201706.scala.SequenceHelpers
import llmTests.shared.scala.Pair
import java.util
import java.util.function.BinaryOperator

class SequenceHelpersImpl extends SequenceHelpers {
  override def of[X](x: X): Sequence[X] = new Sequence[X] {
    override def nextElement: X = x
  }

  override def cyclic[X](l: util.List[X]): Sequence[X] = new Sequence[X] {
    private var i = 0

    override def nextElement: X = {
      val elem = l.get(i)
      i = if (i < l.size() - 1) i + 1 else 0
      elem
    }
  }

  override def incrementing(start: Int, increment: Int): Sequence[Integer] =
    new Sequence[Integer] {
      private var currVal = start
      override def nextElement: Integer = {
        val tmp = Integer.valueOf(currVal)
        currVal += increment
        tmp
      }
    }

  override def accumulating[X](input: Sequence[X], op: BinaryOperator[X]):
  Sequence[X] = new Sequence[X] {
    private var acc = input.nextElement

    override def nextElement: X = {
      val tmp = acc
      acc = op.apply(acc, input.nextElement)
      tmp
    }
  }

  override def zip[X](input: Sequence[X]): Sequence[Pair[X, Integer]] =
    new Sequence[Pair[X, Integer]] {
      private var idx = 0
      override def nextElement: Pair[X, Integer] = {
        val tmp = new Pair[X, Integer](input.nextElement, Integer.valueOf(idx))
        idx += 1
        tmp
      }
    }
}