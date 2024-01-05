package mmlTests.a201602a

import org.junit.Test
import org.junit.Assert._
import mmlTests.a201602a.java.MultiQueue
import mmlTests.a201602a.java.gpt4.MultiQueueImpl

class Test2016a02aScala {

  // In these tests, only multi-queues with queues represented with strings containing integers are considered
  // The solution must be generic!

  @Test
  def testBasic(): Unit = {
    // Create queues Q1 and Q2, and put an element in Q2
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.availableQueues().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    assertEquals(mq.availableQueues(), Set("Q1", "Q2"))
    mq.enqueue(1000, "Q2")
    // Check which queues are empty
    assertTrue(mq.isQueueEmpty("Q1"))
    assertFalse(mq.isQueueEmpty("Q2"))
  }

  @Test
  def testEnqueue(): Unit = {
    // Create queues Q1 and Q2, and put various elements in them
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    mq.enqueue(1000, "Q2")
    mq.enqueue(1001, "Q2")
    mq.enqueue(1002, "Q2")
    mq.enqueue(1003, "Q1")
    mq.enqueue(1004, "Q1")
    // Check which elements are overall in the queue
    assertEquals(mq.allEnqueuedElements(), Set(1000, 1001, 1002, 1003, 1004))
  }

  @Test
  def testDequeue(): Unit = {
    // Create queues Q1 and Q2, and put various elements in them
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    mq.enqueue(1000, "Q2")
    mq.enqueue(1001, "Q2")
    mq.enqueue(1002, "Q2")
    mq.enqueue(1003, "Q1")
    mq.enqueue(1004, "Q1")
    // Check the order of element removal
    assertEquals(mq.dequeue("Q1"), Some(1003))
    assertEquals(mq.dequeue("Q2"), Some(1000))
    assertEquals(mq.dequeue("Q2"), Some(1001))
    assertEquals(mq.dequeue("Q2"), Some(1002))
    assertEquals(mq.dequeue("Q2"), None)
    assertEquals(mq.dequeue("Q2"), None)
    // Other additions and removals...
    mq.enqueue(1005, "Q1")
    mq.enqueue(1006, "Q2")
    assertEquals(mq.dequeue("Q2"), Some(1006))
    assertEquals(mq.allEnqueuedElements(), Set(1004, 1005))
  }

  @Test
  def testFullDequeue(): Unit = {
    // Create queues Q1 and Q2, and put various elements in them
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    mq.enqueue(1000, "Q2")
    mq.enqueue(1001, "Q2")
    mq.enqueue(1002, "Q2")
    mq.enqueue(1003, "Q1")
    mq.enqueue(1004, "Q1")
    // Remove all elements from a queue
    assertEquals(mq.dequeueAllFromQueue("Q2"), List(1000, 1001, 1002))
    assertEquals(mq.dequeueAllFromQueue("Q2").size, 0)
  }

  @Test
  def testDequeueOneFromAll(): Unit = {
    // Create queues Q1, Q2, and Q3, and put various elements in Q1 and Q2
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    mq.openNewQueue("Q3")
    mq.enqueue(1000, "Q2")
    mq.enqueue(1001, "Q2")
    mq.enqueue(1002, "Q2")
    mq.enqueue(1003, "Q1")
    mq.enqueue(1004, "Q1")
    // Remove one element from each queue
    val map = mq.dequeueOneFromAllQueues()
    assertEquals(map.size, 3)
    //assertEquals(map("Q1"), Some(1003))
    //assertEquals(map("Q2"), Some(1000))
    //ssertEquals(map("Q3"), None)
  }

  @Test
  def optionalTestCloseAndReallocate(): Unit = {
    // Create queues Q1, Q2, and Q3, and put various elements in them
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q2")
    mq.openNewQueue("Q3")
    mq.enqueue(1000, "Q2")
    mq.enqueue(1001, "Q2")
    mq.enqueue(1002, "Q2")
    mq.enqueue(1003, "Q1")
    mq.enqueue(1004, "Q1")
    mq.enqueue(1005, "Q3")
    assertEquals(mq.availableQueues(), Set("Q1", "Q2", "Q3"))
    assertEquals(mq.allEnqueuedElements(), Set(1000, 1001, 1002, 1003, 1004, 1005))
    // Close Q1, its elements must go to some other queue
    mq.closeQueueAndReallocate("Q1")
    assertEquals(mq.allEnqueuedElements(), Set(1000, 1001, 1002, 1003, 1004, 1005))
    assertEquals(mq.availableQueues(), Set("Q2", "Q3"))
  }

  @Test
  def optionalTestExceptions(): Unit = {
    // Test various exceptions
    val mq: MultiQueue[Int, String] = new MultiQueueImpl
    assertEquals(mq.allEnqueuedElements().size, 0)
    mq.openNewQueue("Q1")
    mq.openNewQueue("Q3")
    mq.enqueue(1000, "Q1")
    try {
      mq.openNewQueue("Q1")
      fail("can't open Q1 again")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    try {
      mq.isQueueEmpty("Q2")
      fail("can't query a non-existing queue")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    try {
      mq.enqueue(100, "Q2")
      fail("can't add into a non-existing queue")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    try {
      mq.dequeue("Q2")
      fail("can't remove from a non-existing queue")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    try {
      mq.dequeueAllFromQueue("Q2")
      fail("can't remove from a non-existing queue")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    try {
      mq.closeQueueAndReallocate("Q2")
      fail("can't remove from a non-existing queue")
    } catch {
      case _: IllegalArgumentException =>
      case _: Exception => fail("wrong exception thrown")
    }
    mq.closeQueueAndReallocate("Q3")
    // Ok, but now we have only Q1 left.
    try {
      mq.closeQueueAndReallocate("Q1")
      fail("can't close if there's no other queue")
    } catch {
      case _: IllegalStateException =>
      case _: Exception => fail("wrong exception thrown")
    }
  }
}


