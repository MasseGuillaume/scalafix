// package org.example

// import scala.collection
// import scala.collection.immutable
// import scala.collection.mutable.{
//   Map,
//   Set
// } // Challenge to make sure the scoping is correct

// class V0DenotationTest(
//     iset: immutable.Set[Int],
//     cset: collection.Set[Int],
//     imap: immutable.Map[Int, Int],
//     cmap: collection.Map[Int, Int]) {
//   iset + 1
//   iset - 2
//   cset + 1
//   cset - 2

//   cmap + (2 -> 3)
//   cmap + ((4, 5))
//   imap + (2 -> 3)
//   imap + ((4, 5))

//   // Map.zip
//   imap.zip(List())
//   List().zip(List())
// }

trait A
class B
object C

trait X[S <: Any]
trait Y[T]
class Z[S1, T1] extends X[S1] with Y[T1]

object U {
  def orElse[A1 <: A, B1 >: B](that: PartialFunction[A1, B1]): List[(A1, B1)] = Nil
  trait Setting { type T }
  type BooleanSetting <: Setting { type T = Boolean }
}

// case class Person(name: String, age: Int)


// case class Foo(a: Int, b: Int)(c: Int)

// case class F(a: Int)
