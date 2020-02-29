package scala.pragmatic.mytrait

trait Friend {
  val name: String
  def listen = println(s"$name is listening...")
}

class Human(val name: String) extends Friend
class Animal

class Man(override val name: String) extends Human(name)
class Woman(override val name: String) extends Human(name)

class Dog(override val name: String) extends Animal with Friend {
  override def listen: Unit = println(s"$name is listening quietly...")
}

class GirlFriend(override val name: String) extends Human(name) {
  override def listen: Unit = println(s"$name is listening sweetly...")
}

object Main extends App {
  val someone = new Man("Someone")
  val agong = new Dog("A Gong")
  val maChangHui = new GirlFriend("ChangHui Ma")

  someone.listen
  agong.listen
  maChangHui.listen

  def helpWithFriend(friend: Friend) = friend.listen

  helpWithFriend(maChangHui)
}