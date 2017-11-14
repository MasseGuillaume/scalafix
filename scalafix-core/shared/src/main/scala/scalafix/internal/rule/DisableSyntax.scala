package scalafix.internal.rule

import scala.meta._
import scala.meta.contrib.Keyword
import metaconfig.{Conf, Configured}

import scalafix.rule.SemanticRule
import scalafix.util.SemanticdbIndex
import scalafix.rule.{Rule, RuleCtx}
import scalafix.lint.LintMessage
import scalafix.lint.LintCategory
import scalafix.util.SymbolMatcher
import scalafix.internal.config.DisableSyntaxConfig
import scalafix.syntax._

final case class DisableSyntax(config: DisableSyntaxConfig) extends Rule("DisableSyntax") {
  private lazy val errorCategory: LintCategory =
    LintCategory.error(
      """Some constructs are unsafe to use and should be avoided""".stripMargin
    )

  override def init(config: Conf): Configured[Rule] = {
    config
      .getOrElse[DisableSyntaxConfig]("DisableSyntax")(DisableSyntaxConfig.empty)(
        DisableSyntaxConfig.reader)
      .map(DisableSyntax(index, _))
  }

  override def check(ctx: RuleCtx): Seq[LintMessage] = {
    val keywordsLints =
      ctx.tree.tokens.collect {
        case token @ Keyword() if config.keywordsSet.contains(token.text) => {
          errorCategory
            .copy(id = token.text)
            .at(token.pos)
        }
      }

    keywordsLints
  }
}
