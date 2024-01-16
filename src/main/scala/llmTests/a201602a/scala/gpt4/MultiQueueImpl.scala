package llmTests.a201602a.scala.gpt4

import llmTests.a201602a.scala.MultiQueue
import java.util.{HashMap, HashSet, ArrayList}
import scala.util.control.Breaks.{break, breakable}
import scala.jdk.CollectionConverters._
import java.util.{HashSet => JHashSet, HashMap => JHashMap, LinkedList => JLinkedList, Optional => JOptional}

class MultiQueueImpl[T, Q] extends MultiQueue[T, Q] {
  private val queues = new JHashMap[Q, JLinkedList[T]]()

  override def availableQueues(): java.util.Set[Q] = queues.keySet

  override def openNewQueue(queue: Q): Unit = {
    if (queues.containsKey(queue)) {
      throw new IllegalArgumentException
    }
    queues.put(queue, new JLinkedList[T]())
  }

  override def isQueueEmpty(queue: Q): Boolean = {
    validateQueue(queue)
    queues.get(queue).isEmpty
  }

  override def enqueue(elem: T, queue: Q): Unit = {
    validateQueue(queue)
    queues.get(queue).add(elem)
  }

  override def dequeue(queue: Q): java.util.Optional[T] = {
    validateQueue(queue)
    val deque = queues.get(queue)
    if (deque.isEmpty) java.util.Optional.empty[T]()
    else java.util.Optional.ofNullable(deque.removeFirst)
  }

  override def dequeueOneFromAllQueues(): java.util.Map[Q, java.util.Optional[T]] = {
    val results = new JHashMap[Q, java.util.Optional[T]]()
    queues.keySet().toArray.foreach(queue => results.put(queue.asInstanceOf[Q], dequeue(queue.asInstanceOf[Q])))
    results
  }

  private def validateQueue(queue:Q) = {
    if (!queues.containsKey(queue)) throw new IllegalArgumentException
  }

  override def allEnqueuedElements(): java.util.Set[T] = {
    val allItems = new JHashSet[T]()
    queues.values().toArray.foreach(deque => allItems.addAll(deque.asInstanceOf[JLinkedList[T]]))
    allItems
  }

  override def dequeueAllFromQueue(queue: Q): java.util.List[T] = {
    validateQueue(queue)
    val deque = queues.get(queue)
    val allItems = new java.util.ArrayList[T](deque)
    deque.clear()
    allItems
  }

  override def closeQueueAndReallocate(queue: Q): Unit = {
    validateQueue(queue)
    if (queues.size()<=1) throw new IllegalStateException
    val closingQueueDeque = queues.remove(queue)
    val updatedQueues = queues.keySet().toArray.toSeq
    while(!closingQueueDeque.isEmpty){
      updatedQueues.foreach{ queue=>
        if (!closingQueueDeque.isEmpty) queues.get(queue.asInstanceOf[Q]).add(closingQueueDeque.removeFirst())
        else return
      }
    }
  }
}