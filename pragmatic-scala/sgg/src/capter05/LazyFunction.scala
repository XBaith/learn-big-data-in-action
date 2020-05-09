package capter05

<<<<<<< HEAD
object lazyFuction {

=======
/**
  * 惰性函数：当函数返回值被声明为lazy时，函数的执行将被推迟，直到我们首次对此取值，该函数才会被执行
  * 将花费较大的函数推迟到需要使用时才加载
  */
object LazyFunction {

  def main(args: Array[String]): Unit = {
    lazy val res = lazySum(10, 15)
    println("--------")
    println(s"res = $res")
  }

  def lazySum(n1: Int, n2: Int): Int = {
    println("call lazy function")
    n1 + n2;
  }
>>>>>>> 重新拉取repo
}
