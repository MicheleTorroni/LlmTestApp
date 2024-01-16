package llmTests.a201401.scala.gpt4

import java.util
import scala.collection.mutable
import llmTests.a201401.scala.FibonacciAcceptor

class FibonacciAcceptorImpl extends FibonacciAcceptor {

  private var currentSequence: Option[String] = None
  private val sequences: mutable.Map[String, util.ArrayList[Long]] = mutable.Map()

  override def reset(sequenceName: String): Unit = {
    if (sequences.contains(sequenceName)) {
      throw new IllegalArgumentException
    }

    val newSequence = new util.ArrayList[Long]()
    sequences.put(sequenceName, newSequence)
    currentSequence = Some(sequenceName)
  }

  override def consumeNext(l: Long): Boolean = {
    currentSequence match {
      case Some(sequenceName) =>
        val currentList = sequences(sequenceName)
        val size = currentList.size

        if (size < 2 || currentList.get(size - 2) + currentList.get(size - 1) == l) {
          currentList.add(l)
          true
        } else {
          false
        }

      case None =>
        throw new IllegalStateException
    }
  }

  override def getCurrentSequence: util.List[Long] = {
    currentSequence match {
      case Some(sequenceName) =>
        val originalList = sequences(sequenceName)
        val copyList = new util.ArrayList[Long]()
        originalList.forEach(l => copyList.add(l))
        copyList

      case None =>
        throw new IllegalStateException
    }
  }

  override def getAllSequences: util.Map[String, util.List[Long]] = {
    val returnMap = new util.HashMap[String, util.List[Long]]()

    sequences.foreach { case (key, value) =>
      val copyList = new util.ArrayList[Long]()
      value.forEach(l => copyList.add(l))
      returnMap.put(key, copyList)
    }

    returnMap
  }
}