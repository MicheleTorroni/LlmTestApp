package llmTests.a201706.scala

import llmTests.shared.scala.Pair

import java.util
import java.util.{Arrays, List}
import java.util.function.BinaryOperator


/**
 * Utilities for working with sequences, including factories and combinators
 */
trait SequenceHelpers {
  /**
   * @param x
   * @return a sequence always giving value x
   */
  def of[X](x: X): Sequence[X]

  /**
   * @param l
   * @return a sequence cyclically giving all elements in l (for example, if l is [1,2,3]
   *         it gives the sequence representing [1,2,3,1,2,3,1,2,3,1,2,3,...]
   */
  def cyclic[X](l: util.List[X]): Sequence[X]

  /**
   * @param xs
   * @return the same result of cyclic, but with a vararg as input (already implemented via default)
   */
  def make[X](xs: X): Sequence[X] = cyclic(util.Arrays.asList(xs))

  /**
   * @param start
   * @param increment
   * @return the sequence: start, start+increment, start+increment+increment, ...
   */
  def incrementing(start: Int, increment: Int): Sequence[Integer]

  /**
   * @param input
   * @param op
   * @return given sequence s0,s1,s2,s3,.. returns sequence s0, op(s0,s1), op(op(s0,s1),s2),..
   */
  def accumulating[X](input: Sequence[X], op: BinaryOperator[X]): Sequence[X]

  /**
   * @param input
   * @return given sequence s0,s1,s2,s3,.. returns sequence <s0,0>,<s1,1>,<s2,2>,.. (where <..> is Pair)
   */
  def zip[X](input: Sequence[X]): Sequence[Pair[X, Integer]]
}

