package mmlTests.a201603a.scalaTest

import java.util
import java.util.{List, Set}
import java.util.function.Supplier
import java.util.function.ToIntFunction
import java.util.function.UnaryOperator

import mmlTests.a201603a.scalaTest.Bag


/*
 * A factory for Bag, where one can create bags in many different ways.
 */

trait BagFactory { /**
 * @return an empty bag
 */def empty[X]: Bag[X]
  /**
   * @param set
   * @return a bag having one copy of each element in set
   */def fromSet[X](set: util.Set[X]): Bag[X]
  /**
   * @param list
   * @return a bag having one element for each in the list, repetitions included
   */def fromList[X](list: util.List[X]): Bag[X]
  /**
   * @param supplier, a function producing elements of type X (see java.util.Function.Supplier)
   * @param nElements, how many elements will be taken from supplier
   * @param copies, a function giving how many repetition of an element have to be considered (see java.util.Function.ToIntFunction)
   * @return a bag with nElements distinct values of type X, taken from supplier, with number of repetitions obtained from copies
   */def bySupplier[X](supplier: Supplier[X], nElements: Int, copies: ToIntFunction[X]): Bag[X]
  /**
   * @param first, the first element in the bag
   * @param next, a function returning the next element to add in the bag (see java.util.Function.UnaryOperator)
   * @param nElements, how many elements will be considered
   * @param copies, a function giving how many repetition on an element has to be considered (see java.util.Function.ToIntFunction)
   * @return a bag with nElements distinct values of type X, taken from first/next, with number of repetitions obtained from copies
   */def byIteration[X](first: X, next: UnaryOperator[X], nElements: Int, copies: ToIntFunction[X]): Bag[X]
}
