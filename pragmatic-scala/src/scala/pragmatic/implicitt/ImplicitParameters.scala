package scala.pragmatic.implicitt


object ImplicitParameters{

  class Wifi(name:String) {
    override def toString: String = name
  }

  def connectToNetwork(user:String)(implicit wifi : Wifi): Unit ={
    println(s"$user connect to $wifi")
  }

  def atOffice(): Unit ={
    println("---at office---")
    implicit def officeNetwork : Wifi = new Wifi("offic-network")
    val cafeNetWork = new Wifi("cafe-network")

    connectToNetwork("Alex")(cafeNetWork)
    connectToNetwork("Jack")
  }

  def atHome(): Unit ={
    println("---at home---")
    connectToNetwork("Alex")

    implicit def homeNetwork : Wifi = new Wifi("home-network")

    connectToNetwork("Alex")
  }

  def main(args: Array[String]): Unit = {
    atOffice()
    atHome()
  }

}
