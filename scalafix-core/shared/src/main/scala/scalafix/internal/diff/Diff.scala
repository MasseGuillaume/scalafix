package scalafix.internal.diff

import scala.meta.inputs.Input

case class GitChange(start: Int, length: Int)

sealed trait GitDiff
case class NewFile(input: Input) extends GitDiff
case class ModifiedFile(input: Input, changes: List[GitChange]) extends GitDiff
