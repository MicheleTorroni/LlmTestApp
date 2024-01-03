//package MyTest.mmlTests.a201401.Scala.gpt35turbo
//
//import MyTest.mmlTests.a201401.Scala.FibonacciAcceptor
//import scala.collection.mutable.Map
//
//class FibonacciAcceptorImpl extends FibonacciAcceptor {
//
//  private var currentSequence: List[Long] = List.empty
//  private var allSequences: Map[String, List[Long]] = Map.empty
//
//  def reset(sequenceName: String): Unit = {
//    if (allSequences.contains(sequenceName)) {
//      throw new IllegalArgumentException("Cannot create a sequence with existing name!")
//    }
//    currentSequence = List.empty
//    allSequences += (sequenceName -> currentSequence)
//  }
//
//  def consumeNext(l: Long): Boolean = {
//    if (currentSequence.isEmpty) {
//      currentSequence :+= l
//      true
//    } else {
//      val lastIndex = currentSequence.size - 1
//      val secondLast = currentSequence(lastIndex - 1)
//      val last = currentSequence(lastIndex)
//      if (l == secondLast + last) {
//        currentSequence :+= l
//        true
//      } else {
//        false
//      }
//    }
//  }
//
//  def getCurrentSequence: List[Long] = {
//    if (currentSequence.isEmpty) {
//      throw new IllegalStateException("Should reset first!")
//    } else {
//      currentSequence
//    }
//  }
//
//  def getAllSequences: Map[String, List[Long]] = {
//    allSequences.map(entry => entry._1 -> entry._2.toList).toMap
//  }
//}
