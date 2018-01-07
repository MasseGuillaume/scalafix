package test

object ImplicitClassPrivateParams {
  implicit class ValueClassXtensionVal(private val str: Int) extends AnyVal {
    def doubled: Int = str + str
  }

  implicit class ValueClassXtensionAnnotatedVal(@transient private val str: String) extends AnyVal {
    def doubled: String = str + str
  }

  implicit class XtensionVal(private val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedVal(@transient private val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedFinalVal(@transient private final val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionVar(private var str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedVar(@transient private var str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionFinalAnnotatedFinalVar(@transient private final var str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedProtectedVal(@transient protected val str: Int) {
    def doubled: Int = str + str
  }

  implicit class XtensionAnnotatedProtectedVar(@transient protected var str: Int) {
    def doubled: Int = str + str
  }

  implicit class Xtension(str: Int) {
    def doubled: Int = str + str
  }

  class BaseClass(val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionOverride(override val str: String) extends BaseClass(str)
}
