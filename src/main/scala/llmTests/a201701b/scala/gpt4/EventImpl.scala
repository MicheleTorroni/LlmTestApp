package llmTests.a201701b.scala.gpt4

import java.util.function.Supplier
import java.util.{ArrayList, Iterator, Optional}
import llmTests.a201701b.scala.Event
import llmTests.a201701b.scala.Trace
import llmTests.a201701b.scala.TraceFactory

class EventImpl[X](private val time: Int, private val value: X) extends Event[X] {
  override def getTime: Int = time
  override def getValue: X = value
}




