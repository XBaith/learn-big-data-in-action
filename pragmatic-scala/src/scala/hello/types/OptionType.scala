package scala.hello.types

/**
  * Option类型处理有可能会出现null的函数，调用getOrElse()当出现null就会输出错误信息，而不会报空指针异常
  */
object OptionType {

  def ifNull(str : String): Option[String] ={
    if(str.length > 0)
      Some("me")
    else
      None
  }

  def willNull(str : String): String ={
    if(str.length > 0)
      "not null"
    else
      null
  }

  def main(args: Array[String]): Unit = {
    for(input <- Set("fuck",null)){
      val str = ifNull(input)
      var strDisplay = str.getOrElse("null param")
      println(s"input: $input str: $strDisplay")

      //NullPointerException
      var nulStr = willNull("")
    }

  }
}
