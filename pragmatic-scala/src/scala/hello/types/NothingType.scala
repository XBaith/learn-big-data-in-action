package scala.hello.types

import java.util

object NothingType {

  var strList = new util.ArrayList[String]
//  如果没指定反射的类型ArrayList里的类型就是Nothing
  var nothingList = new util.ArrayList

  /*
  Nothing是所有类型的子类，但在集合中Nothing集合不能引用其他类型，其它类型集合也不能引用Nothing
  */
//      nothingList = strList
//      strList = nothingList

  /*
  但是可以在有抛出异常的函数中将其转换为Nothing，如果我们在REPL中写:
  得到的是抽象的Nothing，也得不到Nothing的实例
   */
//      scala> def nothingIfException() = {throw new RuntimeException("Nothing")}
//      nothingIfException: ()Nothing

  def nothingIfException(input: String) = {
    if (input == "fuck"){
      s"$input me"
    }else{
      throw new IllegalArgumentException("fuck you")
    }
  }

  def main(args: Array[String]): Unit = {
    println(nothingIfException("fuck"))
    //依然会抛出异常，因为Nothing只是辅助类型，仅用于类型推断和类型检测
    println(nothingIfException("greetings"))
  }

}
