package llmTests.a201501a.scala.gpt4

import llmTests.a201501a.scala.CoursesCalendar
import llmTests.a201501a.scala.CoursesCalendar.{Day, Room}
import llmTests.shared.java.Pair
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

class CoursesCalendarImpl extends CoursesCalendar {

  private val timetable: mutable.Map[(Day, Room, Int), String] = mutable.Map.empty

  private val slots: List[Int] = List(9, 10, 11, 12, 14, 15, 16, 17)

  def possibleSlots(): java.util.List[Integer] = slots.map(java.lang.Integer.valueOf(_)).asJava

  def bookRoom(d: Day, r: Room, start: Int, duration: Int, course: String): Unit = {
    require(slots.contains(start), "Invalid start hour.")
    require(slots.contains(start + duration - 1), "Invalid end hour.")

    val hours = start until (start + duration)

    for (timeSlot <- hours) {
      if (timetable.contains((d, r, timeSlot))) throw new IllegalStateException("Cannot book room. Time slot is already occupied.")
    }

    for (timeSlot <- hours) {
      timetable += ((d, r, timeSlot) -> course)
    }
  }

  def dayRoomSlots(d: Day, r: Room): java.util.Set[Pair[Integer,String]] = {
    timetable.filter { case ((day, room, _), _) => day == d && room == r  }
      .map { case ((_, _, hour), course) => new Pair[Integer, String](java.lang.Integer.valueOf(hour), course) }.toSet.asJava
  }

  def courseSlots(course: String): java.util.Map[Pair[Day, Room], java.util.Set[Integer]] = {
    val courseMap = timetable.filter { case (_, c) => c == course }
    val result = mutable.Map.empty[Pair[Day, Room], Set[Integer]]

    for (((day, room, hour), _) <- courseMap) {
      val key = new Pair[Day, Room](day, room)
      result.get(key) match {
        case Some(set) => result.update(key, set + hour)
        case None => result += (key -> Set(hour))
      }
    }

    val mapJava = result.map { case (pair, set) => (pair, set.map(java.lang.Integer.valueOf(_)).asJava) }.toMap.asJava

    mapJava
  }
}