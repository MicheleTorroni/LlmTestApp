package mmlTests.a201602a.scala

import scala.collection.mutable
import scala.collection.mutable.{Set, Map, ListBuffer}

trait MultiQueue[T, Q] {

  /**
   * @return the set of queues currently available (or working)
   */
  def availableQueues(): Set[Q]

  /**
   * @param creates a new queue
   * @throws IllegalArgumentException if the queue is already available
   */
  def openNewQueue(queue: Q): Unit

  /**
   * @param queue is the queue we check
   * @return whether the queue is empty
   * @throws IllegalArgumentException if the queue is not available
   */
  def isQueueEmpty(queue: Q): Boolean

  /**
   * @param elem is the element to add
   * @param queue is the queue where the element is to be added
   * @throws IllegalArgumentException if the queue is not available
   */
  def enqueue(elem: T, queue: Q): Unit

  /**
   * @param queue is the queue where to take the next element
   * @return the next element in the queue, or empty if there's none
   * @throws IllegalArgumentException if the queue is not available
   */
  def dequeue(queue: Q): Option[T]

  /**
   * dequeues one element from any queue, where possible
   * @return a map of dequeued elements
   */
  def dequeueOneFromAllQueues(): Map[Q, Option[T]]

  /**
   * @return the set of all enqueued elements
   */
  def allEnqueuedElements(): Set[T]

  /**
   * Empties a queue
   * @param queue the queue to be emptied
   * @return the list of elements enqueued
   * @throws IllegalArgumentException if the queue is not available
   */
  def dequeueAllFromQueue(queue: Q): List[T]

  /**
   * Empties a queue and moves all of its elements to some other available queue
   * @param queue the queue to be emptied
   * @throws IllegalArgumentException if the queue is not available
   * @throws IllegalStateException if there's no alternative queue for moving elements to
   */
  def closeQueueAndReallocate(queue: Q): Unit
}

