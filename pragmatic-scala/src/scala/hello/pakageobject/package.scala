package scala.hello

package object pakageobject {
  var result : String = _
  def toBinaryString(num: Int) : String = {
    var temp = num
    while(temp != 0){
      val remainder = temp % 2
      result += remainder
      temp /= 2
    }
    result.replace("null","").reverse
  }
}

/*object binaryTest{
  def main(args: Array[String]): Unit = {
    println(obs.toHexString(3))
  }
}*/
