package llmTests.a201603a.scala

import java.util
/**
 * An immutable bag of elements of type X: once created it can't be modified
 * A bag is a multiset, namely, a set where you can have many copies of an element
 */trait Bag[X] { /**
 * @param x
 * @return the number of elements x in this bag (0 if none exists)
 */def numberOfCopies(x: X): Int
  /**
   * @return the number of elements in this bag, considering also repetitions
   */def size: Int
  /**
   * @return a copy of this bag as a java.util.List, where ordering does not matter
   */def toList: util.List[X]
}
