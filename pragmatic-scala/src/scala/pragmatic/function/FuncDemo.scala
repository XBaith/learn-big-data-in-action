package scala.pragmatic.function

<<<<<<< HEAD
object funcDemo {
=======
object FuncDemo {
>>>>>>> 重新拉取repo
  def main(args: Array[String]): Unit = {
    println(totalResult(1, 100, n => n))
    println("偶数之和：" + totalResult(1, 100, n => if(n % 2 == 0) n else 0))
    println("奇数之和：" + totalResult(1, 100, n => if(n % 2 != 0) n else 0))
  }

  /**
    * 按某个函数来求一个区间的和
    * @param left　下界
    * @param right  上届
    * @param code　函数
    * @return 整型值
    */
  def totalResult(left: Int, right: Int, code: Int => Int) : Int = {
    var res = 0
    for( i <- left to right ) {
      res += code(i)
    }
    res
  }
}
