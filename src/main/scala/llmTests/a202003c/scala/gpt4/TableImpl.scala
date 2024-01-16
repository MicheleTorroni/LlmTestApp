//package mmlTests.a202003c.scala.gpt4
//
//import mmlTests.shared.java.Pair
//import mmlTests.a202003c.java.TableFactory
//import mmlTests.a202003c.java.Table
//import scala.collection.mutable
//
//
//class TableImpl[R, C, V](private val content: mutable.Map[(R, C), V]) extends Table[R, C, V] {
//  override def rows() = content.keys.map(_._1).toSet
//  override def columns() = content.keys.map(_._2).toSet
//  override def asRowMap() = content.groupBy(_._1._1).view.mapValues(_.map { case ((_, c), v) => c -> v }).toMap
//  override def asColumnMap() = content.groupBy(_._1._2).view.mapValues(_.map { case ((r, _), v) => r -> v }).toMap
//  override def getValue(row: R, column: C) = content.get((row, column))
//  override def putValue(row: R, column: C, value: V) = content += ((row, column) -> value)
//}
//
