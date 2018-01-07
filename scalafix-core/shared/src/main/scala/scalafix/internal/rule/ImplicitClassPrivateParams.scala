package scalafix.internal.rule

import scala.meta.Term.Param
import scala.meta.{Defn, Mod}
import scalafix.rule.RuleCtx
import scalafix.{Patch, Rule}

case object ImplicitClassPrivateParams extends Rule("ImplicitClassPrivateParams") {

  override def description: String =
  "Prevent public access of val or var implicit class parameters by adding private modifier"

  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case Defn.Class(mods, _, _, primCtor, _) if hasImplicitMod(mods) && atLeastOneNonPrivateOrProtectedParam(primCtor.paramss) =>

        val patches = for {
          paramList <- primCtor.paramss
          param <- paramList if isNonPrivateOrProtectedParam(param)
          anchorMod <- retrieveAnchorMod(param.mods)
        } yield ctx.addLeft(anchorMod, "private")
        patches.asPatch

    }.asPatch
  }

  private def retrieveAnchorMod(mods: List[Mod]): Option[Mod] = {
    mods match {
      case (_ :Mod.ValParam) | (_ :Mod.VarParam) :: _ => Some(mods.head)
      case (_ :Mod.Final) :: (_ :Mod.VarParam) | (_ :Mod.ValParam) :: _ => Some(mods(1))
      case (_ :Mod.Annot) :: (_ :Mod.VarParam) | (_ :Mod.ValParam) :: _ => Some(mods(1))
      case (_ :Mod.Annot) :: (_ :Mod.Final) :: (_ :Mod.ValParam) | (_ :Mod.VarParam) :: _ => Some(mods(1))
      case _ => None
    }
  }

  private def isNonPrivateOrProtectedParam(param: Param): Boolean =
    !param.mods.exists {
      case (_: Mod.Private) | (_ : Mod.Protected) => true
      case _ => false
    }

  private def atLeastOneNonPrivateOrProtectedParam(params: List[List[Param]]): Boolean =
    params.flatten.exists(isNonPrivateOrProtectedParam)

  private def hasImplicitMod(mods: List[Mod]): Boolean =
    mods.exists {
      case _: Mod.Implicit => true
      case _ => false
    }
}
