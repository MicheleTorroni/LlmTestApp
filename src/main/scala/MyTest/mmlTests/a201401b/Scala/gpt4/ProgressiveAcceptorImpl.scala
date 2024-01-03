package MyTest.mmlTests.a201401b.Scala.gpt4

import MyTest.mmlTests.a201401b.Scala.Aggregator;
import MyTest.mmlTests.a201401b.Scala.ProgressiveAcceptor;
import MyTest.mmlTests.a201401b.Scala.ProgressiveFilter;

import scala.collection.mutable

class ProgressiveAcceptorImpl[X] extends ProgressiveAcceptor[X] {
  private var aggregator: Option[Aggregator[X]] = None
  private var progressiveFilter: Option[ProgressiveFilter[X]] = None
  private var size: Int = 0
  private var elements = mutable.Map[Int, X]()

  def setAggregator(aggregator: Aggregator[X]): Unit = {
    require(aggregator != null, "Aggregator cannot be null")
    this.aggregator = Some(aggregator)
  }

  def setProgressiveFilter(filter: ProgressiveFilter[X]): Unit = {
    require(filter != null, "Filter cannot be null")
    this.progressiveFilter = Some(filter)
  }

  def setSize(size: Int): Unit = {
    require(size >= 0, "Size cannot be negative")
    this.size = size
  }

  def accept(pos: Int, elem: X): Boolean = {
    require(aggregator.isDefined, "Aggregator is not defined")
    require(progressiveFilter.isDefined, "Filter is not defined")
    require(size > 0, "Size is not defined")
    if (pos >= size) return false
    if (pos > 0 && elements.contains(pos - 1) && !progressiveFilter.get.isNextOK(elements(pos - 1), elem)) return false
//    elements = elements.filterKeys(_ <= pos)
//    elements(pos) = elem
    true
  }

  def aggregateAll(): X = {
    require(elements.nonEmpty, "There are no elements to aggregate")
    elements.values.reduce(aggregator.get.aggregate)
  }
}