//package mmlTests.a201602a.scala.gpt35turbo
//
//import mmlTests.a201602a.scala.MultiQueue
//
//import scala.collection.mutable
//
//
//class MultiQueueImpl[T, Q] extends MultiQueue[T, Q] {
//
//  private val queues: mutable.Map[Q, mutable.Queue[T]] = mutable.Map.empty
//
//  override def availableQueues(): Set[Q] = queues.keySet.toSet
//
//  override def openNewQueue(queue: Q): Unit = {
//    if (queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue already available")
//    } else {
//      queues.put(queue, mutable.Queue.empty)
//    }
//  }
//
//  override def isQueueEmpty(queue: Q): Boolean = {
//    if (!queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue not available")
//    } else {
//      queues(queue).isEmpty
//    }
//  }
//
//  override def enqueue(elem: T, queue: Q): Unit = {
//    if (!queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue not available")
//    } else {
//      queues(queue).enqueue(elem)
//    }
//  }
//
//  override def dequeue(queue: Q): Option[T] = {
//    if (!queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue not available")
//    } else {
//      queues(queue).dequeueFirst(_ => true)
//    }
//  }
//
//  override def dequeueOneFromAllQueues(): Map[Q, Option[T]] = {
//    val result: mutable.Map[Q, Option[T]] = mutable.Map.empty
//    for ((queue, q) <- queues) {
//      result.put(queue, q.dequeueFirst(_ => true))
//    }
//    result.toMap
//  }
//
//  override def allEnqueuedElements(): Set[T] = {
//    val result: mutable.Set[T] = mutable.Set.empty
//    for (q <- queues.values) {
//      result.addAll(q)
//    }
//    result.toSet
//  }
//
//  override def dequeueAllFromQueue(queue: Q): List[T] = {
//    if (!queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue not available")
//    } else {
//      queues(queue).dequeueAll(_.asInstanceOf[Any]).toList
//    }
//  }
//
//  override def closeQueueAndReallocate(queue: Q): Unit = {
//    if (!queues.contains(queue)) {
//      throw new IllegalArgumentException("Queue not available")
//    } else if (queues.size == 1) {
//      throw new IllegalStateException("No alternative queue for moving elements to")
//    } else {
//      val elements = queues(queue).dequeueAll(_.asInstanceOf[Any]).toList
//      queues.remove(queue)
//      for ((otherQueue, q) <- queues if otherQueue != queue) {
//        q.enqueueAll(elements)
//      }
//    }
//  }
//}
