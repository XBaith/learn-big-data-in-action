package scala.pragmatic

class Computer(val id: Int, val name: String) {
  override def toString = s"ID: $id\tName: $name"
}

/**
  * 由于只有主构造器才能调用父类的构造器，所以继承一个类时，子类的参数必须传递给父类的某个构造器中。
  * @param id
  * @param name
  * @param price
  */
class Laptop(override val id: Int, override val name: String, val price: Double) extends Computer(id, name){
  override def toString: String = s"${super.toString}\tPrice: $price"
}

object Main{
  def main(args: Array[String]): Unit = {
    val Acer = new Laptop(1,"acer", 5000)
    println(Acer)
  }
}

