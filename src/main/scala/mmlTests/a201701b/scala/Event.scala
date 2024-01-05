package mmlTests.a201701b.scala

/**
 * An immutable representation of an event happened at a given discrete time, carrying a value 
 */
trait Event[X] {
  def getTime: Int

  def getValue: X
}
