package scala.hello.types

object EitherType {

  def divide(input : Int) : Either[Double,String] = {
    if(input != 0){
      Left(5 / input)
    }else{
      Right("Invail number, you can't divide 0")
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
