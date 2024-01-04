package mmlTests.a201401b.scala

trait Aggregator[X] {
  def aggregate(one: X, two: X): X
}
