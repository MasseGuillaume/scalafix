package scalafix.tests.core

import scala.meta._
import System.{lineSeparator => nl}

class V0DenotationSuite extends BaseSemanticSuite("V0DenotationTest") {


  test("convert methods") {
    object TestDenotation {
      private val denotations = Map(
        "orElse" ->
          """|def orElse: [A1, B1] => (that: PartialFunction[A1, B1]): List[Tuple2[A1, B1]]
             |  [13..17): that => org/example/U.orElse().(that)
             |  [19..34): PartialFunction => scala/PartialFunction#
             |  [35..37): A1 => org/example/U.orElse().[A1]
             |  [39..41): B1 => org/example/U.orElse().[B1]
             |  [45..49): List => scala/package.List#
             |  [50..56): Tuple2 => scala/Tuple2#
             |  [57..59): A1 => org/example/U.orElse().[A1]
             |  [61..63): B1 => org/example/U.orElse().[B1]""".stripMargin,
        "cmap" ->
          """|private val method cmap: Map[Int, Int]
             |  [0..3): Map => scala/collection/Map#
             |  [4..7): Int => scala/Int#
             |  [9..12): Int => scala/Int#""".stripMargin,
        "cset" ->
          """|private val method cset: Set[Int]
             |  [0..3): Set => scala/collection/Set#
             |  [4..7): Int => scala/Int#""".stripMargin,
        "imap" ->
          """|private val method imap: Map[Int, Int]
             |  [0..3): Map => scala/collection/immutable/Map#
             |  [4..7): Int => scala/Int#
             |  [9..12): Int => scala/Int#""".stripMargin,
        "iset" ->
          """|private val method iset: Set[Int]
             |  [0..3): Set => scala/collection/immutable/Set#
             |  [4..7): Int => scala/Int#""".stripMargin
      )
      def unapply(n: Term.Name): Option[String] = denotations.get(n.syntax)
    }
    val converted =
      source
        .collect {
          case tree @ TestDenotation(expected) =>
            index
              .denotation(tree)
              .map(obtained => (obtained.toString, expected))
        }
        .flatten
        .toSet
        .toList

    val (obtained, expected) = converted.unzip

    def show(xs: List[String]): String = xs.mkString(nl)

    assertNoDiff(show(obtained), show(expected))
  }




  // test("convert class") {
  //   source.collect{
  //     case tree =>
  //       index.denotation(tree).foreach{ denot =>
  //         println("----")
  //         println(tree)
  //         println(denot)
  //       }
  //   }
  // }

  // test("convert type") {

  // }

// type T: Boolean
//   [0..7): Boolean => _root_.scala.Boolean#


// val List: List.type
//   [0..4): List => _root_.scala.collection.immutable.List.

//   }

// param that: PartialFunction[A1, B1]
//   [0..15): PartialFunction => scala/PartialFunction#
//   [16..18): A1 => org/example/U.orElse().[A1]
//   [20..22): B1 => org/example/U.orElse().[B1]

// package fix

  // test("convert value") {

  // }
}
