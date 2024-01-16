package llmTests.a201701b.scala.gpt4

import llmTests.a201701b.scala.Event
import llmTests.a201701b.scala.Trace
import llmTests.a201701b.scala.TraceFactory
import llmTests.a201701b.scala.gpt4.{EventImpl, TraceImpl}

import java.util.function.Supplier
import java.util.{ArrayList, Iterator, Optional}

class TraceFactoryImpl extends TraceFactory {
  override def fromSuppliers[X](sdeltaTime: Supplier[Integer], svalue: Supplier[X], size: Int): Trace[X] = {
    val events = new ArrayList[Event[X]](size)
    var time = 0
    for (_ <- 0 until size) {
      events.add(new EventImpl[X](time, svalue.get()))
      time += sdeltaTime.get()
    }
    new TraceImpl[X](events)
  }
  override def constant[X](sdeltaTime: Supplier[Integer], value: X, size: Int): Trace[X] = {
    val svalue = new Supplier[X]() { override def get(): X = value }
    fromSuppliers(sdeltaTime, svalue, size)
  }
  override def discrete[X](svalue: Supplier[X], size: Int): Trace[X] = {
    val sdeltaTime = new Supplier[Integer]() { override def get(): Integer = 1 }
    fromSuppliers(sdeltaTime, svalue, size)
  }
}

