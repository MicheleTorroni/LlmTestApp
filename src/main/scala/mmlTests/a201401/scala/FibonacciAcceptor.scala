package mmlTests.a201401.scala

/**
 * This trait is used to control a GUI that asks a user for one or more Fibonacci sequences, and then provides operations to
 * subsequently extract such sequences.
 * A sequence is Fibonacci if it starts with any pair of numbers, and then every other number is the sum of the two preceding ones.
 * For example, a sequence is: 1 3 4 7 11 18 29.., another is: 1 1 2 3 5 8..
 * When implementing this trait, in addition to considering the comments to the methods below, be careful that each data structure
 * returned does not violate the encapsulation (i.e. by modifying it, the FibonacciAcceptor object that returned it is not adversely affected),
 * for example, create "defensive copies"!
 */

trait FibonacciAcceptor {

  /**
   * Initiates a new sequence
   *
   * @param sequenceName the name of the sequence
   * @throws IllegalArgumentException if sequenceName was already used in a previous sequence
   */
  @throws(classOf[IllegalArgumentException])
  def reset(sequenceName: String): Unit

  /**
   * Consumes the next element of the current sequence.
   *
   * @param l the next element
   * @return false if that element is not correct
   * @throws IllegalStateException if no reset was initially done
   */
  @throws(classOf[IllegalStateException])
  def consumeNext(l: Long): Boolean

  /**
   * @return the current sequence
   * @throws IllegalStateException if no reset was initially done
   */
  @throws(classOf[IllegalStateException])
  def getCurrentSequence: List[Long]

  /**
   * @return all sequences produced so far, as a map from names to sequences
   */
  def getAllSequences: Map[String, List[Long]]
}
