package llmTests.a201401b.scala.gpt35turbo

import llmTests.a201401b.scala.{Aggregator, ProgressiveAcceptor, ProgressiveFilter}

class ProgressiveAcceptorImpl[X] extends ProgressiveAcceptor[X] {

  private var filter: ProgressiveFilter[X] = _
  private var aggregator: Aggregator[X] = _
  private var size: Int = _
  private var sequence: Seq[X] = Seq.empty

  override def setProgressiveFilter(filter: ProgressiveFilter[X]): Unit = {
    this.filter = filter
  }

  override def setAggregator(aggregator: Aggregator[X]): Unit = {
    this.aggregator = aggregator
  }

  override def setSize(size: Int): Unit = {
    if (size <= 0) {
      throw new IllegalArgumentException("Not negative!")
    }
    this.size = size
  }

  override def accept(pos: Int, elem: X): Boolean = {
    if (filter == null || aggregator == null || size <= 0) {
      throw new IllegalStateException("Not correctly initialized")
    }

    if (pos < 0 || pos > size) {
      throw new IndexOutOfBoundsException("Invalid position")
    }

    val isAccepted = if (pos == 0 || filter.isNextOK(sequence(pos - 1), elem)) {
      if (pos <= sequence.length) {
        sequence = sequence.take(pos)
      }
      sequence = sequence :+ elem
      true
    } else {
      false
    }

    if (sequence.length > size) {
      sequence = sequence.takeRight(size)
    }

    isAccepted
  }

  override def aggregateAll(): X = {
    if (aggregator == null || sequence.isEmpty) {
      throw new IllegalStateException("Not correctly initialized")
    }

    sequence.reduceOption(aggregator.aggregate).getOrElse(throw new IllegalStateException("Empty sequence"))
  }
}