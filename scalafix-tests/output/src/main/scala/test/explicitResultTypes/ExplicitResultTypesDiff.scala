package test.explicitResultTypes

object ExplicitResultTypesDiff {
  def complex = List(1)
  def complexAgain: _root_.scala.collection.immutable.List[_root_.scala.Int] = List(1)
  println("")
}
 