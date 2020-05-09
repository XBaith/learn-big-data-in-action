package scala.pragmatic.function

/**
  * 柯里化
  */
object CurryingDemo {
  def main(args: Array[String]): Unit = {
    foo(1)(2)(3).foreach(e => print(e + " "))
    println()
    foo(1){2}{3}.foreach(e => print(e + " "))
  }

  def foo(a: Int)(b: Int)(c: Int) = {
    val arr = Array(a, b, c)
    arr
  }
}
