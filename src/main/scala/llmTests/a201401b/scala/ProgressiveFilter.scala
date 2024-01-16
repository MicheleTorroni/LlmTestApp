package llmTests.a201401b.scala

trait ProgressiveFilter[X] {
  def isNextOK(previous: X, next: X): Boolean
}