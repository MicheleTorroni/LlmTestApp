//package mmlTests.a201501a.scala.gpt4
//
//import mmlTests.a201501a.scala.CoursesCalendar
//import mmlTests.shared.scala.Pair
//
//import scala.collection.mutable.{HashMap, HashSet}
//
//class CoursesCalendarImpl extends CoursesCalendar {
//
//  private val booking = HashMap[Pair[Day,Room], HashSet[Pair[Int,String]]]()
//  private val courses = HashMap[String, HashMap[Pair[Day,Room], HashSet[Int]]]()
//
//  override def possibleSlots(): List[Int] = {
//    List(9, 10, 11, 12, 14, 15, 16, 17)
//  }
//
//  override def bookRoom(d: Day, r: Room, start: Int, duration: Int, course: String): Unit = {
//    val key = new Pair[Day,Room](d,r)
//    val valList = Array.fill(duration)(new Pair(start, course))
//    if(!booking.contains(key)){
//      booking += key -> HashSet(valList: _*)
//    } else {
//      if((booking(key) intersect HashSet(valList: _*)).nonEmpty){
//        throw new IllegalStateException()
//      } else {
//        booking(key) ++= HashSet[Pair[Int,String]](valList: _*)
//      }
//    }
//    val key2 = new Pair[Day,Room](d,r)
//    val valList2 = Array.tabulate(duration)(_ + start)
//    if(!courses.contains(course)) {
//      courses += course -> HashMap[Pair[Day,Room], HashSet[Int]]()
//    }
//    if(!courses(course).contains(key2)){
//      courses(course) += key2 -> HashSet[Int](valList2: _*)
//    } else {
//      courses(course)(key2) ++= HashSet[Int](valList2: _*)
//    }
//  }
//
//  override def dayRoomSlots(d: Day, r: Room): Set[Pair[Int, String]] = {
//    val pair = new Pair[Day, Room](d, r)
//    booking.getOrElse(pair, Set())
//  }
//
//  override def courseSlots(course: String): Map[Pair[Day, Room], Set[Int]] = {
//    courses.getOrElse(course, Map())
//  }
//}
