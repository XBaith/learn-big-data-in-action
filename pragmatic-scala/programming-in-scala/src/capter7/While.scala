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

    b
  }

  def gcd(x: Long, y: Long): Long =
    if (y == 0) x
    else gcd(y, x % y)

  def main(args: Array[String]): Unit = {
    println(gcdLoop(66, 42))
    println(gcd(66, 42))
  }
}
