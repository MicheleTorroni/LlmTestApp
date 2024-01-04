package mmlTests.a201501a.scala

import mmlTests.shared.scala.Pair

trait CoursesCalendar {

  /**
   * Working days of the week
   */
  enum Day {
    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
  }

  /**
   * Available rooms
   */
  enum Room {
    case A, B, C, D, E, MAGNA, VELA, C_ALDO_MORO
  }

  /**
   * Returns the hours in a day in which rooms can be possibly booked
   */
  def possibleSlots(): List[Int]

  /**
   * Books a lesson
   *
   * @param d        day
   * @param r        room
   * @param start    initial hour
   * @param duration number of consecutive hours
   * @param course   course name
   * @throws IllegalStateException if some hours are already booked, in which case no change to booking is done
   */
  def bookRoom(d: Day, r: Room, start: Int, duration: Int, course: String): Unit

  /**
   * Yields all booking in a given day and room
   *
   * @param d day
   * @param r room
   * @return a set of pairs of initial hour and course name
   */
  def dayRoomSlots(d: Day, r: Room): Set[Pair[Int, String]]

  /**
   * Yields all booking of a course
   *
   * @param course the course to inspect
   * @param r      room
   * @return a map from pairs of day and room, to sets of initial hours
   */
  def courseSlots(course: String): Map[Pair[Day, Room], Set[Int]]
}
