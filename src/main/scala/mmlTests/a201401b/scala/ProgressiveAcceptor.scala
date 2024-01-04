package mmlTests.a201401b.scala

trait ProgressiveAcceptor[X] {

  /**
   * Set the filter this acceptor will use.
   *
   * @param filter the filter to set
   */
  def setProgressiveFilter(filter: ProgressiveFilter[X]): Unit

  /**
   * Set the aggregator this acceptor will use.
   *
   * @param aggregator the aggregator to set
   */
  def setAggregator(aggregator: Aggregator[X]): Unit

  /**
   * Set the maximum size of the sequence to accept.
   *
   * @param size the maximum size of the sequence
   */
  def setSize(size: Int): Unit

  /**
   * Accept a new element of the sequence. An element is accepted if it is OK with respect to the previous one
   * according to the filter. Ideally, elements are accepted in the order of position 0, 1, 2, 3,..
   * If one re-accepts a previous element, the subsequent part of the sequence is erased and needs to be re-accepted.
   * For instance, if the sequence accepts 10, 20, 30, 40 in positions 0, 1, 2, 3, and then one re-accepts 21 in position 1,
   * the new sequence is 10, 21 (30 and 40 are erased).
   *
   * @param pos  the position of the element
   * @param elem the element
   * @return true if the element is accepted
   */
  def accept(pos: Int, elem: X): Boolean

  /**
   * Return the single value that the current sequence aggregates to.
   *
   * @return the aggregated value
   */
  def aggregateAll(): X
}