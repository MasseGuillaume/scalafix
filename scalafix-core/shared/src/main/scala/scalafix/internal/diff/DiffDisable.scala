package scalafix.internal.diff

import scala.meta.{Input, Position, AbsolutePath}

import scala.collection.mutable.StringBuilder

import scalafix.internal.util.IntervalSet

object DiffDisable {
  def empty: DiffDisable = EmptyDiff
  def apply(diffs: List[GitDiff]): DiffDisable = new FullDiffDisable(diffs)
}

sealed trait DiffDisable {
  def isDisabled(position: Position, sourceroot: AbsolutePath): Boolean
  def isDisabled(file: Input, sourceroot: AbsolutePath): Boolean
}

private object EmptyDiff extends DiffDisable {
  def isDisabled(position: Position, sourceroot: AbsolutePath): Boolean = false
  def isDisabled(file: Input, sourceroot: AbsolutePath): Boolean = false
}

private class FullDiffDisable(diffs: List[GitDiff]) extends DiffDisable {
  private val newFiles: Set[AbsolutePath] = diffs.collect {
    case NewFile(path) => path
  }.toSet

  private val modifiedFiles: Map[AbsolutePath, IntervalSet] = diffs.collect {
    case ModifiedFile(path, changes) => {
      val ranges = changes.map {
        case GitChange(start, end) => (start, end - 1)
      }
      path -> IntervalSet(ranges)
    }
  }.toMap

  def toPath(file: Input, sourceroot: AbsolutePath): AbsolutePath = {
    file match {
      case Input.VirtualFile(relativePath, _) =>
        sourceroot.resolve(relativePath)
      case Input.File(absolutePath, _) => absolutePath
      case _ => throw new Exception("expecting VirtualFile or File")
    }
  }

  def isDisabled(file: Input, sourceroot: AbsolutePath): Boolean = {
    val path = toPath(file, sourceroot)
    !(newFiles.contains(path) || modifiedFiles.contains(path))
  }

  def isDisabled(position: Position, sourceroot: AbsolutePath): Boolean = {
    val path = toPath(position.input, sourceroot)

    def isAddition: Boolean =
      newFiles.contains(path)

    def isModification: Boolean = {
      val startLine = position.startLine
      val endLine = position.endLine
      modifiedFiles
        .get(path)
        .fold(false)(
          interval =>
            interval.intersects(
              startLine,
              endLine
          )
        )
    }

    !(isAddition || isModification)
  }

  override def toString: String = {

    val b = new StringBuilder()
    def add(in: String): Unit =
      b ++= in + "\n"

    add("== New Files ==")
    diffs.foreach {
      case NewFile(path) => add(path.toString)
      case _ => ()
    }

    add("== Modified Files ==")
    diffs.foreach {
      case ModifiedFile(path, changes) => {
        add(path.toString)
        changes.foreach {
          case GitChange(start, end) => add(s"  [${start + 1}, ${end + 1}]")
        }
      }
      case _ => ()
    }
    b.result()
  }
}
