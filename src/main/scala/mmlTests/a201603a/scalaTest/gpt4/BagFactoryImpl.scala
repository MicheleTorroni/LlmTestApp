//package mmlTests.a201603a.scalaTest.gpt4
//
//import mmlTests.a201603a.scalaTest.{Bag, BagFactory}
//import java.util.function.{ToIntFunction, UnaryOperator}
//import scala.collection.mutable
//
//class BagFactoryImpl extends BagFactory {
//
//  override def empty[X](): Bag[X] = {
//    new BagImpl[X](Map.empty[X, Int])
//  }
//
//  override def fromSet[X](set: Set[X]): Bag[X] = {
//    new BagImpl[X](set.map(_ -> 1).toMap)
//  }
//
//  override def fromList[X](list: List[X]): Bag[X] = {
//    new BagImpl[X](list.groupBy(identity).view.mapValues(_.size).toMap)
//  }
//
//  override def bySupplier[X](supplier: () => X, nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
//    val map = mutable.Map.empty[X, Int]
//    for (_ <- 1 to nElements) {
//      val x = supplier()
//      map(x) = copies.applyAsInt(x)
//    }
//    new BagImpl[X](map.toMap)
//  }
//
//  override def byIteration[X](first: X, next: UnaryOperator[X], nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
//    var current = first
//    val map = mutable.Map.empty[X, Int]
//    for (_ <- 1 to nElements) {
//      map(current) = copies.applyAsInt(current)
//      current = next.apply(current)
//    }
//    new BagImpl[X](map.toMap)
//  }
//}
