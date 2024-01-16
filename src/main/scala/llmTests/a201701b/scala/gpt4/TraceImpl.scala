package llmTests.a201701b.scala.gpt4

import llmTests.a201701b.scala.Event
import llmTests.a201701b.scala.Trace

import java.util.{ArrayList, Iterator, Optional}

class TraceImpl[X](private var events: ArrayList[Event[X]]) extends Trace[X] {
  var currentIndex: Int = 0
  override def nextEvent: Optional[Event[X]] =
    if (currentIndex == events.size()) Optional.empty() else {
      val event = events.get(currentIndex)
      currentIndex += 1
      Optional.of(event)
    }
  override def iterator: Iterator[Event[X]] = events.iterator()
  override def skipAfter(time: Int): Unit = {
    while (currentIndex < events.size() && events.get(currentIndex).getTime <= time) currentIndex += 1
  }
  override def combineWith(trace: Trace[X]): Trace[X] = {
    val list = new ArrayList[Event[X]](events)
    trace.iterator.forEachRemaining(event => list.add(event))
    list.sort((e1, e2) => Integer.compare(e1.getTime, e2.getTime))
    new TraceImpl[X](list)
  }
  override def dropValues(value: X): Trace[X] = {
    val list = new ArrayList[Event[X]]()
    for (i <- 0 until events.size()) {
      val event = events.get(i)
      if (!event.getValue.equals(value)) list.add(event)
    }
    new TraceImpl[X](list)
  }
}

