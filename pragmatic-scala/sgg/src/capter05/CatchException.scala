package capter05

<<<<<<< HEAD
object CatchExecption {

  def main(args: Array[String]): Unit = {
      
=======
/**
  * 回顾：Java中的异常需要先捕获小的异常再执行大的异常，Scala中可以反过来
  * Scala中：异常与Java一致，不同的是catch后通过case exception : Exception => ...进行匹配
  * 每个case可以匹配一种异常
  *
  * Scala中没有编译异常，只有运行时的异常
  */
object CatchException {

  def main(args: Array[String]): Unit = {
    try {
      val res = 10 / 0
    }catch {
      case ex : ArithmeticException => println("捕获除数为零异常")
      case ex : Exception => println("捕获异常")
    }finally {
      println("捕获完成")
    }

    println("捕获后继续执行")
>>>>>>> 重新拉取repo
  }

}
