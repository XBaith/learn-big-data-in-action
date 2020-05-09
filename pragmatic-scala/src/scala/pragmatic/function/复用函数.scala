package scala.pragmatic.function

object 复用函数 {
  def main(args: Array[String]): Unit = {
    def calculator(input: Int): Int = {println(s"calc with $input"); input}

    val c1 = new MyClass(calculator)
    val c2 = new MyClass(calculator)

    c1.call(5)
    c2.call(10)
  }
}

class MyClass(func: Int => Int) {
  def call(input: Int): Unit = {
    print("Called... ")
    func(input)
  }
}
