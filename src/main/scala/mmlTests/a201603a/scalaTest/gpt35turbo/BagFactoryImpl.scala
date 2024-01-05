//package mmlTests.a201603a.scala.gpt35turbo
//
//import mmlTests.a201603a.scala.Bag
//import mmlTests.a201603a.scala.gpt35turbo.BagImpl
//
//class BagFactoryImpl extends mmlTests.a201603a.scala.BagFactory {
//  override def empty[X](): Bag[X] = new BagImpl[X](Nil)
//
//  override def fromSet[X](set: Set[X]): Bag[X] = new BagImpl[X](set.toList)
//
//  override def fromList[X](list: List[X]): Bag[X] = new BagImpl[X](list)
//
//  override def bySupplier[X](supplier: () => X, nElements: Int, copies: X => Int): Bag[X] = {
//    val elements = (0 until nElements).flatMap(_ => {
//      val x = supplier()
//      val numCopies = copies(x)
//      List.fill(numCopies)(x)
//    }).toList
//    new BagImpl[X](elements)
//  }
//
//  override def byIteration[X](first: X, next: X => X, nElements: Int, copies: X => Int): Bag[X] = {
//    val elements = (0 until nElements).flatMap(i => {
//      val x = next(first)
//      val numCopies = copies(x)
//      List.fill(numCopies)(x)
//    }).toList
//    new BagImpl[X](elements)
//  }
//}
//
