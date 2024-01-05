package mmlTests.a201602a.scala.gpt4

import mmlTests.a201602a.scala.MultiQueue

import scala.collection.mutable
import scala.collection.immutable.Set

import scala.collection.mutable


import scala.collection.{mutable, immutable}

class MultiQueueImpl[T, Q] extends MultiQueue[T, Q] {

  private val queues = mutable.Map[Q, mutable.Queue[T]]()

  def availableQueues(): mutable.Set[Q] = {
    mutable.Set[Q]() ++= queues.keySet
  }

  def openNewQueue(queue: Q): Unit = {
    if (queues.contains(queue)) throw new IllegalArgumentException
    else queues(queue) = mutable.Queue[T]()
  }

  def isQueueEmpty(queue: Q): Boolean = {
    if (!queues.contains(queue)) throw new IllegalArgumentException
    else queues(queue).isEmpty
  }

  def enqueue(elem: T, queue: Q): Unit = {
    if (!queues.contains(queue)) throw new IllegalArgumentException
    else queues(queue) += elem
  }

  def dequeue(queue: Q): Option[T] = {
    if (!queues.contains(queue)) throw new IllegalArgumentException
    else if (queues(queue).isEmpty) None
    else Some(queues(queue).dequeue())
  }

  def dequeueOneFromAllQueues(): mutable.Map[Q, Option[T]] = {
    val map = mutable.Map[Q, Option[T]]()
    for (queue <- queues.keys) {
      map(queue) = dequeue(queue)
    }
    map
  }

  def allEnqueuedElements(): mutable.Set[T] = {
    val allElements = mutable.Set[T]()
    for (queue <- queues.values) {
      allElements ++= queue
    }
    allElements
  }

  def dequeueAllFromQueue(queue: Q): List[T] = {
    if (!queues.contains(queue)) throw new IllegalArgumentException
    else {
      val dequeued = queues(queue).toList
      queues(queue).clear()
      dequeued
    }
  }

  def closeQueueAndReallocate(queue: Q): Unit = {
    if (!queues.contains(queue)) throw new IllegalArgumentException
    if (queues.size == 1) throw new IllegalStateException
    else {
      val elementsToReallocate = queues(queue)
      queues -= queue
      for (element <- elementsToReallocate) {
        queues(queues.keys.head) += element
      }
    }
  }
}