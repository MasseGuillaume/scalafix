/* ONLY
rules = ImplicitClassPrivateParams
 */
package test

object ImplicitClassPrivateParams {
  implicit class ValueClassXtensionVal(val str: Int) extends AnyVal {
    def doubled: Int = str + str
  }

  implicit class ValueClassXtensionAnnotatedVal(@transient val str: String) extends AnyVal {
    def doubled: String = str + str
  }

  implicit class XtensionVal(val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedVal(@transient val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedFinalVal(@transient final val str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionVar(var str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionAnnotatedVar(@transient var str: String) {
    def doubled: String = str + str
  }

  implicit class XtensionFinalAnnotatedFinalVar(@transient final var str: String) {
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
