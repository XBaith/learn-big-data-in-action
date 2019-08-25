package scala.hello.variance

object CovarianceAndContravariance {

  class Computer(val name: String){
    override def toString: String = name
  }

  class Laptop(override val name : String) extends Computer(name)

  //指定泛型的上界，可以让scala支持协变，也就是说函数可以接受Computer类包括自己和所有的子类
  def workWithComputer[L <: Computer] (computers: Array[L]): Unit = {
    println("work with computers: " + computers.mkString(", "))
  }

  //指定泛型的下界，可以让scala支持逆变，也就是说函数可以接受L类包括自己和所有的父类
  def copyComputer[L,C >: L] (fromComputers: Array[L], toComputers: Array[C]): Unit ={
    val nums = fromComputers.length
    var i = 0
    while(i != nums){
      toComputers(i) = fromComputers(i)
      i = i + 1
    }
  }

  def main(args: Array[String]): Unit = {
    val laptops = Array(new Laptop("Acer"),new Laptop("Mac"))
    workWithComputer(laptops)
    val computers = new Array[Computer](2)
    copyComputer(laptops,computers)
    workWithComputer(computers)
  }

}
