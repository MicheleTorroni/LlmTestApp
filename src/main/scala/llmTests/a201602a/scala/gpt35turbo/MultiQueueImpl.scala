//package llmTests.a201602a.scala.gpt35turbo
//
//import java.util
//import llmTests.a201602a.scala.MultiQueue
//
//class MultiQueueImpl[T, Q] extends MultiQueue[T, Q] {
//
//  private var queues: Map[Q, util.Queue[T]] = Map.empty
//
//  override def availableQueues(): util.Set[Q] = queues.keySet
//
//  override def openNewQueue(queue: Q): Unit = {
//    if (queues.contains(queue)) throw new IllegalArgumentException()
//    queues += (queue -> new util.LinkedList[T]())
//  }
//
//  override def isQueueEmpty(queue: Q): Boolean = {
//    if (!queues.contains(queue)) throw new IllegalArgumentException()
//    queues(queue).isEmpty
//  }
//
//  override def enqueue(elem: T, queue: Q): Unit = {
//    if (!queues.contains(queue)) throw new IllegalArgumentException()
//    queues(queue).add(elem)
//  }
//
//  override def dequeue(queue: Q): util.Optional[T] = {
//    if (!queues.contains(queue)) throw new IllegalArgumentException()
//    val q = queues(queue)
//    if (q.isEmpty) util.Optional.empty() else util.Optional.of(q.poll())
//  }
//
//  override def dequeueOneFromAllQueues(): util.Map[Q, util.Optional[T]] = {
//    val map = new util.HashMap[Q, util.Optional[T]]()
//    for (queue <- queues.keySet) {
//      map.put(queue, dequeue(queue))
//    }
//    map
//  }
//
//  override def allEnqueuedElements(): util.Set[T] = {
//    val set = new util.HashSet[T]()
//    for (queue <- queues.keySet) {
//      set.addAll(queues(queue))
//    }
//    set
//  }
//
//  override def dequeueAllFromQueue(queue: Q): util.List[T] = {
//    if (!queues.contains(queue)) throw new IllegalArgumentException()
//    val list = new util.ArrayList[T]()
//    val q = queues(queue)
//    while (!q.isEmpty) {
//      list.add(q.poll())
//    }
//    list
//  }
//
//  override def closeQueueAndReallocate(queue: Q): Unit = {
//    if (!queues.contains(queue)) throw new IllegalArgumentException()
//    if (queues.size == 1) throw new IllegalStateException()
//    val elements = dequeueAllFromQueue(queue)
//    queues -= queue
//    val altQueue = queues.head._1
//    queues(altQueue).addAll(elements)
//  }
//}