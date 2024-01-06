package mmlTests.a201902a.scala.gpt35turbo

class Task[T](val task: T, val prerequisites: Set[T], var isExecuted: Boolean = false)

