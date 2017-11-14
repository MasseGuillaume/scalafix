package scalafix.internal.config

import metaconfig.ConfDecoder
import org.langmeta._
import MetaconfigPendingUpstream.XtensionConfScalafix

case class DisableSyntaxConfig(
    keywords: List[DisableSyntaxConfig.Keyword] = Nil
) {
  implicit val reader: ConfDecoder[DisableSyntaxConfig] =
    ConfDecoder.instanceF[DisableSyntaxConfig](
      _.getField(keywords).map(a => DisableSyntaxConfig(a))
    )
}

object DisableSyntaxConfig {
  val empty = DisableSyntaxConfig()
  implicit val reader: ConfDecoder[DisableSyntaxConfig] = empty.reader

  case class Keyword(value: String)
  object Keyword {
    implicit val readerKeyword: ConfDecoder[Keyword] =
      ReaderUtil.fromMap(all.map(x => x.show -> x).toMap)
  }
}

  // Abstract
  // Case
  // Catch
  // Class
  // Def
  // Do
  // Else
  // Enum
  // Extends
  // False
  // Final
  // Finally
  // For
  // Forsome
  // If
  // Implicit
  // Import
  // Lazy
  // Match
  // Macro
  // New
  // Null
  // Object
  // Override
  // Package
  // Private
  // Protected
  // Return
  // Sealed
  // Super
  // This
  // Throw
  // Trait
  // True
  // Try
  // Type
  // Val
  // Var
  // While
  // With
  // Yield

  // xml (Xml.Start)
  // quasiquote (Interpolation.Start)
  // carriageReturn (\r) (CR)
  // pageBreak (\f) (LF)
  // tab (\t) (Tab)
  // semicolon (;) (Semicolon)

  // constants (Constant.)
  //   integer
  //   long
  //   float
  //   double
  //   character (Char)
  //   symbol
  //   string