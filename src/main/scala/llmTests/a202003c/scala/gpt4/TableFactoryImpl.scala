//package mmlTests.a202003c.scala.gpt4
//
//import mmlTests.a202003c.java.{Table, TableFactory}
//import mmlTests.shared.java.Pair
//import mmlTests.a202003c.java.gpt4.TableFactoryImpl
//
//import scala.collection.mutable
//
//class TableFactoryImpl extends TableFactory {
//  override def fromMap[R, C, V](map: Map[Pair[R, C], V]) =
//    new TableImpl(mutable.Map() ++ map.map { case (pair, value) => (pair.getFst, pair.getSnd) -> value })
//
//  override def fromFunction[R, C, V](rows: Set[R], columns: Set[C], valueFunction: (R, C) => V) =
//    new TableImpl(mutable.Map() ++ (for { r <- rows; c <- columns } yield (r -> c) -> valueFunction(r, c)).toMap.unzip)
//
//  override def graph[G](edges: Set[Pair[G, G]]) = {
//    val nodes = edges.flatMap(edge => Set(edge.getFst, edge.getSnd))
//    fromFunction(nodes, nodes, (r: G, c: G) => edges.contains(new Pair(r, c)))
//  }
//
//  override def squareMatrix[V](values: Array[Array[V]]) = {
//    val rowColValues = for { (r, c) <- values.zipWithIndex; v = r._2 } yield (c, c) -> v
//    new TableImpl(mutable.Map(rowColValues: _*))
//  }
//}
//
