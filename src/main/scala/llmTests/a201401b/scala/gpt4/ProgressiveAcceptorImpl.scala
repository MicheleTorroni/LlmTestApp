package llmTests.a201401b.scala.gpt4

import llmTests.a201401b.scala.{Aggregator, ProgressiveAcceptor, ProgressiveFilter}

import scala.collection.mutable

class ProgressiveAcceptorImpl[X] extends ProgressiveAcceptor[X] {

  private var aggregator: Option[Aggregator[X]] = None
  private var progressiveFilter: Option[ProgressiveFilter[X]] = None
  private var size: Option[Int] = None
  private var sequence: mutable.ArrayBuffer[X] = mutable.ArrayBuffer.empty[X]

  override def setProgressiveFilter(filter: ProgressiveFilter[X]): Unit =
    if (filter != null) progressiveFilter = Some(filter)
    else throw new NullPointerException

  override def setAggregator(agg: Aggregator[X]): Unit =
    if (agg != null) aggregator = Some(agg)
    else throw new NullPointerException

  override def setSize(s: Int): Unit =
    if (s >= 0) size = Some(s)
    else throw new IllegalArgumentException

  override def accept(pos: Int, elem: X): Boolean = (progressiveFilter, aggregator, size) match {
    case (Some(filter), Some(_), Some(s)) =>
      if (0 <= pos && pos < s) {
        if (pos >= sequence.length) {
          sequence += elem
          if (pos == 0 || filter.isNextOK(sequence(pos - 1), sequence(pos))) true
          else {
            sequence = sequence.dropRight(1)
            false
          }
        } else {
          val accept = if (pos == 0) true else filter.isNextOK(sequence(pos - 1), elem)
          if (accept) {
            sequence = sequence.take(pos) :+ elem
          }
          accept
        }
      } else false
    case _ => throw new IllegalStateException("ProgressiveAcceptor is not properly initialized")
  }

  override def aggregateAll(): X = aggregator match {
    case Some(agg) =>
      if (sequence.isEmpty) throw new IllegalStateException("Nothing to aggregate")
      else if (sequence.length == 1) sequence.head
      else sequence.reduceLeft(agg.aggregate)
    case None => throw new NullPointerException("Aggregator is missing")
  }
}