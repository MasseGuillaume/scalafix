package scalafix.internal.diff

import scala.meta.AbsolutePath

case class GitChange(start: Int, length: Int)

sealed trait GitDiff
case class NewFile(path: AbsolutePath) extends GitDiff
case class ModifiedFile(path: AbsolutePath, changes: List[GitChange])
    extends GitDiff
