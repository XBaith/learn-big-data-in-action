package scala.pragmatic.function

object FoldLeft {
<<<<<<< HEAD
=======
  def main(args: Array[String]): Unit = {
    val arr = Array(1, 2, 3, 4, 5)
    print("数组：")
    val arrElements = arr.foreach(element => print(element + " "))

    println("\n数组求和：" + arr.foldLeft(0){ (n1, n2) => n1 + n2 } )
    println("数组中的最大值：" + arr.foldLeft(Integer.MIN_VALUE) { (n1, n2) => Math.max(n1, n2) })
  }
>>>>>>> 重新拉取repo

}
