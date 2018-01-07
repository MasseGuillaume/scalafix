---
layout: docs
title: ImplicitClassPrivateParams
---

# ImplicitClassPrivateParams

`val` and `var` fields of an implicit class are accessible as an extension. 
This rule adds the `private` access modifier in such cases in order to prevent direct access.

```scala
// before
implicit class XtensionVal(val str: String) {
  def doubled: String = str + str
}
"message".str // compiles

// after
implicit class XtensionValFixed(private val strFixed: String) {
  def doubled: String = strFixed + strFixed
}
"message".strFixed // does not compile
```
