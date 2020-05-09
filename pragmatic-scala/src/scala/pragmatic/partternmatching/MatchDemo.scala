package scala.pragmatic.partternmatching

<<<<<<< HEAD
object MatchDemo {

=======
/**
  * 模式匹配可以匹配任意类型
  */
object MatchDemo {
  def main(args: Array[String]): Unit = {
    val days = List("星期三", "星期五")
    days.foreach{ schedule }

    matchList(List("red", "blue"))
    matchList(List("red", "green"))
    matchList(List("red"))
    matchList(List("green", "red", "blue"))
    matchList(List("yellow"))

    matchAny(1)
    matchAny((10.3, "百分之"))
  }

  def schedule(date: String) = {
    date match {
      case "星期一" => println("做作业")
      case "星期二" => println("上网课")
      case "星期三" => println("学大数据")
      case "星期四" => println("上选修课")
      case "星期五" => println("敲代码")
      case "星期六" => println("打游戏")
      case "星期七" => println("写作业")
      case _ => "随便敲点代码" /*_是通配符，其他未设置输入都将匹配*/
    }
  }

  /**
    * 枚举类型可以避免出现匹配异常
    */
  object DAY extends Enumeration {
    val MONDAY: DAY.Value = Value("星期一")
    val TUESDAY: DAY.Value = Value("星期二")
    val WEDNESDAY: DAY.Value = Value("星期三")
  }

  /**
    * 匹配List[String]类型
    * @param list
    */
  def matchList(list: List[String]) = {
    list match {
      case List("red", "blue") => println("red and blue")
      case List("red", _*) => println("red and other colors") // _* 表示通配符，后面可以有其他元素也可以没有
      case List("green" , otherColors @ _*) => println("green and " + otherColors)  //@ _* 表示其他的List[String]
      case _ => println("no case to match...")
    }
  }

  def matchAny(any: Any) = {
    any match {
      case _: Int => println("Int")
      case (_: Double, _: String) => println("Double and String tuple")
      case _: List[String] => println("List[String]")
      case _ => println("not match yet")
    }
  }
>>>>>>> 重新拉取repo
}
