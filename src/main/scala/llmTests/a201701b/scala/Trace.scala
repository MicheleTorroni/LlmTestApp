package llmTests.a201701b.scala

import java.util
import java.util.{Iterator, Optional}


/**
 * An immutable representation of a finite sequence of events (with increasing time) to be accessed once and sequentially
 * It uses class java.util.Optional, please check its documentation. Recall that:
 * - you create an empty Optional with: Optional.empty() 
 * - you create a non-empty Optional with: Optional.of(10)
 * - you check if an optional is not empty with: Optional.isPresent()
 * - you get the content of an optional with: Optional.get()
 */trait Trace[X] { /**
 * @return the next event, if any; as this is returned the next call will give the successive element
 */def nextEvent: Optional[Event[X]]
  /**
   * @return an iterator over the trace
   */def iterator: util.Iterator[Event[X]]
  /**
   * @param time
   * It skips all the events until reaching the first event in the future of time 
   */def skipAfter(time: Int): Unit
  /**
   * @param trace: an input trace
   * Creates a new trace, obtained consuming and combining this and trace: recall that the result should 
   * respect temporal ordering of events 
   */def combineWith(trace: Trace[X]): Trace[X]
  /**
   * @param value
   * Creates a new trace, obtained from this (and consuming it): events with content equal to value are dropped away
   */def dropValues(value: X): Trace[X]
}
