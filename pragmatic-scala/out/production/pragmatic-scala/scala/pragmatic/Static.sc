import scala.collection._

class Computer private (val name : String){
  println(s"Create $name Computer")

  override def toString: String = s"Computer: $name"
}

object Computer{
  private val computers = mutable.Map(
    "Acer" -> new Computer("Acer"),
    "Mac" -> new Computer("Mac"),
    "HuaWei" -> new Computer("HuaWei")
  )
  def haveComputers() : Iterable[String] = computers.keys

  def getComputer (name: String) : Computer = computers.getOrElseUpdate(name, new Computer(name))
}

println(s"We have ${Computer.haveComputers()}")
println(Computer.getComputer("Acer"))
println(Computer.getComputer("Mac"))
println(Computer.getComputer("XiaoMi"))
