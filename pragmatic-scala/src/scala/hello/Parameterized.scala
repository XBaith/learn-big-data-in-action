package scala.hello

object Parameterized {

  def echo[T](value1: T, value2: T): Unit ={
    println(s"First Object: $value1(${value1.getClass})\tSecond Object: $value2(${value2.getClass})")
  }

  class MyMap[K, V](val key: K, val value: V){
    override def toString: String = s"$key\t$value"

    def isEquals(otherKey: K, otherValue: V): Boolean ={
      if(otherKey != null && otherValue != null){
        key == otherKey && value == otherValue
      }else{
        throw new IllegalArgumentException("param is illegal!")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    echo("fuck","this")
    echo(1,3)
    echo("type",100)

    val myMap = new MyMap[String, Int]("fuck",1000)
    println(myMap.isEquals("fuck",1000))
    println(myMap.isEquals("cost",100))
  }
}
