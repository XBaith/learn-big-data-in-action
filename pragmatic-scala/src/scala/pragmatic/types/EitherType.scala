package scala.pragmatic.types

/**
  * 当一个函数需要返回两个值，返回值可以为Either。left是期望得到的值，right不是期望得到的值
  */
object EitherType {

  def divide(input : Int) : Either[Double,String] = {
    if(input != 0){
      Left(5 / input)
    }else{
      Right("Invail number, can't divide 0")
    }
  }

  def displayResult(result : Either[Double,String]): Unit = {
    println(s"Raw: $result")
    result match {
      case Left(value) => println(s"Divided result: $value")
      case Right(value) => println(s"Error: $value")
    }
  }

  def main(args: Array[String]): Unit = {
    displayResult(divide(10))
    displayResult(divide(0))
  }

}
