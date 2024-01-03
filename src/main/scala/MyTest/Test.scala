package MyTest

object myList {
  sealed trait List[+T]
  case class Nil[T]() extends List[T]
  case class Cons[T](head: T, tail: List[T]) extends List[T]

  def sum(list: List[Int]): Int = list match {
    case Nil() => 0
    case Cons(head, tail) => head + sum(tail)
  }

  def map[A, B](list: List[A], f: A => B): List[B] = list match {
    case Nil() => Nil()
    case Cons(head, tail) => Cons(f(head), map(tail, f))
  }

  def filter[A](list: List[A], p: A => Boolean): List[A] = list match {
    case Cons(head, tail) if p(head) => Cons(head, filter(tail, p))
    case Cons(_, tail) => filter(tail, p)
    case Nil() => Nil()
  }
}