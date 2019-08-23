package scala.hello

object Hello {

  def main(args: Array[String]) = {
    val (s1, s2) = syaHello()
    val numbers = Array(5,1,3,10,9,999)

    println(s"$s1 $s2")
    println(getMax(numbers:_*))

    defaultParam("Li", "Er")
    defaultParam("Wang")
    defaultParam()

    //混用默认参数值和命名参数值
    defaultParam(lastName = "Hu");
  }

  def syaHello() = {
    ("Hello", "Scala")
  }

  /**
    * 传递变长参数值 Int*
    * 隐式省略return 默认将最后一个表达式返回
    * @param nums
    * @return
    */
  def getMax(nums:Int*): Int ={
    var max = Integer.MIN_VALUE
    for(n <- nums)
      if(n > max)
        max = n

    max
  }

  /**
    * 函数设置默认参数值
    * @param firstName
    * @param lastName
    */
  def defaultParam(firstName:String = "Bai",lastName:String = "Xu")={
    println(s"My first name is $firstName, last name is $lastName")
  }
}
