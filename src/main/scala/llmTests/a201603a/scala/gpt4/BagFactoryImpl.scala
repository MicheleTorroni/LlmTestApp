package llmTests.a201603a.scala.gpt4

import llmTests.a201603a.scala.Bag
import llmTests.a201603a.scala.BagFactory
import java.util
import java.util.function.{Supplier, ToIntFunction, UnaryOperator}
import scala.jdk.CollectionConverters._
import scala.collection.immutable.List

class BagFactoryImpl extends BagFactory{
  override def empty[X]: Bag[X] = new BagImpl[X](Nil)

  override def fromSet[X](set: util.Set[X]): Bag[X] = {
    new BagImpl[X](set.asScala.toList)
  }

  override def fromList[X](list: util.List[X]): Bag[X] = {
    new BagImpl[X](list.asScala.toList)
  }

  override def bySupplier[X](supplier: Supplier[X], nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
    val list = List.fill(nElements)(supplier.get()).flatMap(x => List.fill(copies.applyAsInt(x))(x))
    new BagImpl[X](list)
  }

  override def byIteration[X](first: X, next: UnaryOperator[X], nElements: Int, copies: ToIntFunction[X]): Bag[X] = {
    var element = first
    var list = List.fill(copies.applyAsInt(element))(element)
    for (_ <- 1 until nElements) {
      element = next.apply(element)
      list = list ++ List.fill(copies.applyAsInt(element))(element)
    }
    new BagImpl[X](list)
  }

  private class BagImpl[X](val elems: List[X]) extends Bag[X]{
    override def numberOfCopies(x: X): Int = elems.count(_ == x)

    override def size: Int = elems.size

    override def toList: util.List[X] = elems.asJava
  }
}