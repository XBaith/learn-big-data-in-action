package capter7

object While {
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while(a != 0){
      val temp = a
      a = b % a
      b = temp
    }
<<<<<<< HEAD

    b
  }

  def gcd(x: Long, y: Long): Long =
    if (y == 0) x
    else gcd(y, x % y)
=======
    b
  }

  def gcd(x: Long, y: Long): Long = if (y == 0) x else gcd(y, x % y)
>>>>>>> 重新拉取repo

  def main(args: Array[String]): Unit = {
    println(gcdLoop(66, 42))
    println(gcd(66, 42))
<<<<<<< HEAD
  }
=======

    /*break关键字代替方案*/
    import util.control.Breaks._
    var n = 0
    breakable (
      while (true) {
        print(n + " ")
        if(n == 20) break()
        else n += 1
      }
    )

    println("\nwhile is done!")
  }


>>>>>>> 重新拉取repo
}
