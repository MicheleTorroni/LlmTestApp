package llmTests.a201701b.scala

import java.util.function.Supplier


/**
 * A factory interface for traces
 * Recall that a Supplier<X> (a functional interface) has a method get() that provides you 
 * with elements of type X
 */trait TraceFactory { /**
 * @param sdeltaTime: a supplier of positive int values, representing increments of time
 * @param svalue: a supplier of values
 * @param size
 * @return a trace of length size, initial time 0, incrementing time by what sdeltaTime supplies each time, 
 * and values provided by svalue supplier
 */def fromSuppliers[X](sdeltaTime: Supplier[Integer], svalue: Supplier[X], size: Int): Trace[X]
  /**
   * @param sdeltaTime: a supplier of positive int values
   * @param value: a value
   * @param size
   * @return a trace of length size, initial time 0, incrementing time by sdeltaTime, 
   * and values always equal to value
   */def constant[X](sdeltaTime: Supplier[Integer], value: X, size: Int): Trace[X]
  /**
   * @param svalue: a supplier of values
   * @param size
   * @return a trace of length size, time 0,1,2,3... and values provided by svalue
   */def discrete[X](svalue: Supplier[X], size: Int): Trace[X]
}
