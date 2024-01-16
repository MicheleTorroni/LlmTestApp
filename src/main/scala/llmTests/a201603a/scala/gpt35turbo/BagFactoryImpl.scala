//package llmTests.a201603a.scala.gpt35turbo
//
//import java.util
//import java.util.{List, Set}
//import java.util.function.Supplier
//import java.util.function.ToIntFunction
//import java.util.function.UnaryOperator
//
//import llmTests.a201603a.scala.Bag
//import llmTests.a201603a.scala.BagFactory
//
//class BagFactoryImpl extends BagFactory {
//  def empty[X]: Bag[X] = new BagImpl[X](Map())
//
//  def fromSet[X](set: util.Set[X]): Bag[X] = {
//    var map = Map[X, Int]()
//    set.forEach(x => map += (x -> (map.getOrElse(x, 0) + 1)))
//    new BagImpl(map)
//  }
//
//  def fromList[X](list: util.List[X]): Bag[X] = {
//    var map = Map[X, Int]()
//    list.forEach(x => map += (x -> (map.getOrElse(x, 0) + 1)))
//    new BagImpl(map)
//  }
//
//  def bySupplier[X](supplier: Supplier[X], nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
//    var map = Map[X, Int]()
//    for (_ <- 0 until nElements) {
//      val x = supplier.get()
//      val count = copies.applyAsInt(x)
//      map += (x -> (map.getOrElse(x, 0) + count))
//    }
//    new BagImpl(map)
//  }
//
//  def byIteration[X](first: X, next: UnaryOperator[X], nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
//    var map = Map[X, Int]()
//    var current = first
//    for (_ <- 0 until nElements) {
//      val count = copies.applyAsInt(current)
//      map += (current -> (map.getOrElse(current, 0) + count))
//      current = next.apply(current)
//    }
//    new BagImpl(map)
//  }
//}
