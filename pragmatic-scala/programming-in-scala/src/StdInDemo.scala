import scala.io.StdIn

object StdInDemo {
  def main(args: Array[String]): Unit = {
    println(s"你输入的数字是 : ${StdIn.readInt()}")
  }
}
