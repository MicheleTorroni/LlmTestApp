package MyTest.mmlTests.a201401b.Scala.gpt35turbo

import MyTest.mmlTests.a201401b.Scala.Aggregator;
import MyTest.mmlTests.a201401b.Scala.ProgressiveAcceptor;
import MyTest.mmlTests.a201401b.Scala.ProgressiveFilter;

class ProgressiveAcceptorImpl[X] extends ProgressiveAcceptor[X] {

  private var filter: ProgressiveFilter[X] = _
  private var aggregator: Aggregator[X] = _
  private var size: Int = _
  private var sequence: List[X] = Nil

  override def setProgressiveFilter(filter: ProgressiveFilter[X]): Unit = {
    this.filter = filter
  }

  override def setAggregator(aggregator: Aggregator[X]): Unit = {
    this.aggregator = aggregator
  }

  override def setSize(size: Int): Unit = {
    if (size < 0) {
      throw new IllegalArgumentException("Not negative!")
    }
    this.size = size
  }

  override def accept(pos: Int, elem: X): Boolean = {
    if (filter == null || aggregator == null || size == 0) {
      throw new IllegalStateException("Not correctly initialized")
    }
    if (pos < sequence.length) {
      // Remove elements after the position
      sequence = sequence.take(pos)
    }
    if (pos != sequence.length) {
      throw new IllegalArgumentException("Wrong position")
    }
    if (pos == size) {
      false
    } else {
      val previous = sequence.headOption.getOrElse(elem)
      if (filter.isNextOK(previous, elem)) {
        sequence = elem :: sequence
        true
      } else {
        false
      }
    }
  }

  override def aggregateAll(): X = {
    if (aggregator == null) {
      throw new NullPointerException("Aggregator not set")
    }
    sequence.reduceOption(aggregator.aggregate).getOrElse(throw new IllegalStateException("No elements"))
  }
}