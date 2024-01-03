package MyTest.mmlTests.a201401b.Scala

trait ProgressiveFilter[X] {
  def isNextOK(previous: X, next: X): Boolean
}