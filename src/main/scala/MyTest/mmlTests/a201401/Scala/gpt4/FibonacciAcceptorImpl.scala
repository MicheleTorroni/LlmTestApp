package MyTest.mmlTests.a201401.Scala.gpt4

import MyTest.mmlTests.a201401.Scala.FibonacciAcceptor

import scala.collection.mutable

class FibonacciAcceptorImpl extends FibonacciAcceptor {

  private var sequences: mutable.Map[String, List[Long]] = mutable.Map.empty
  private var currentSequence: String = ""

  def reset(sequenceName: String): Unit = {
    if (sequences.contains(sequenceName)) {
      throw new IllegalArgumentException("Sequence name already exists")
    }
    sequences += (sequenceName -> List.empty[Long])
    currentSequence = sequenceName
  }

  def consumeNext(n: Long): Boolean = {
    sequences.get(currentSequence) match {
      case None => throw new IllegalStateException("No sequence selected")
      case Some(list) =>
        if (list.length < 2 || (list(list.length - 1) + list(list.length - 2) == n)) {
          sequences(currentSequence) = list :+ n
          true
        } else false
    }
  }

  def getCurrentSequence: List[Long] = sequences.get(currentSequence) match {
    case None => throw new IllegalStateException("No sequence selected")
    case Some(list) => list
  }

  def getAllSequences: Map[String, List[Long]] = sequences.toMap
}
