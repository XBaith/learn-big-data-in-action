package scala.hello.types

/**
  * Option类型处理有可能会出现null的函数，调用getOrElse()当出现null就会输出错误信息，而不会报空指针异常
  */
object OptionType {

  def ifNull(str : String): Option[String] ={
    if(str == "fuck")
      Some("me")
    else
      None
  }

  def main(args: Array[String]): Unit = {
    for(input <- Set("fuck",null)){
      val str = ifNull(input)
      val strDisplay = str.getOrElse("null param")
      println(s"Input: $input\t|\tResult: row-->$str\t|\tdisplay-->$strDisplay")
    }

  }
}
