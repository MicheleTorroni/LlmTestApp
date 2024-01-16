package llmTests.a201401.scala.gpt35turbo

import java.util
import llmTests.a201401.scala.FibonacciAcceptor

class FibonacciAcceptorImpl extends FibonacciAcceptor {

  private val sequences: util.Map[String, util.List[Long]] = new util.HashMap[String, util.List[Long]]()
  private var currentSequence: util.List[Long] = _

  override def reset(sequenceName: String): Unit = {
    if (sequences.containsKey(sequenceName)) {
      throw new IllegalArgumentException("Sequence name already used")
    }
    currentSequence = new util.ArrayList[Long]()
    sequences.put(sequenceName, currentSequence)
  }

  override def consumeNext(l: Long): Boolean = {
    if (currentSequence == null) {
      throw new IllegalStateException("No reset was initially done")
    }
    if (currentSequence.isEmpty || currentSequence.size == 1) {
      currentSequence.add(l)
      true
    } else {
      val size = currentSequence.size
      val element1 = currentSequence.get(size - 1)
      val element2 = currentSequence.get(size - 2)
      if (l == element1 + element2) {
        currentSequence.add(l)
        true
      } else {
        false
      }
    }
  }

  override def getCurrentSequence: util.List[Long] = {
    if (currentSequence == null) {
      throw new IllegalStateException("No reset was initially done")
    }
    new util.ArrayList[Long](currentSequence)
  }

  override def getAllSequences: util.Map[String, util.List[Long]] = {
    val map = new util.HashMap[String, util.List[Long]]()
    val iterator = sequences.entrySet().iterator()
    while (iterator.hasNext) {
      val entry = iterator.next()
      map.put(entry.getKey, new util.ArrayList[Long](entry.getValue))
    }
    map
  }
}
