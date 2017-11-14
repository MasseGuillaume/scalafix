/* ONLY
rule = DisableSyntax
DisableSyntax.keywords = [
  "null"
  "return"
  "throw"
  "var"
  "while"
]
*/
package test

case object DisableSyntax {

  case class B()
  val y = B().asInstanceOf[String] // assert: Disable.asInstanceOf
  val z = 1.asInstanceOf[String] // assert: Disable.asInstanceOf
  val x = "2".asInstanceOf[Int] // assert: Disable.asInstanceOf
  val w = List(1, 2, 3).asInstanceOf[Seq[String]] // assert: Disable.asInstanceOf

  case class D() {
    def disabledFunction: Boolean = true
  }
  val zz = D().disabledFunction // assert: Disable.disabledFunction

  case class C() {
    def asInstanceOf: String = "test"
  }
  val xx = C().asInstanceOf // OK, no errors

  trait A {
    type O
  }
  object AA extends A {
    type O = String
    def asInstanceOf: O = "test"
  }
  val yy = AA.asInstanceOf // OK, no errors
  Option(1).get // assert: Disable.get

  null // assert: Disable.null
  def foo: Int = {
    return 0 // assert: Disable.return
  }
  throw new Exception("Boom") // assert: Disable.throw
  var ok = false // assert: Disable.var
  while (!ok) { // assert: Disable.while
    ok = true
  }
  implicit class Nope(v: Any) // assert: Disable.implicit
}
