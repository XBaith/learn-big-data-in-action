package scala.pragmatic.mytrait

<<<<<<< HEAD
=======
/**
  * 特质内的变量是抽象的，需要继承的类来提供
  */
>>>>>>> 重新拉取repo
trait Friend {
  val name: String
  def listen = println(s"$name is listening...")
}

class Human(val name: String) extends Friend
class Animal

class Man(override val name: String) extends Human(name)
class Woman(override val name: String) extends Human(name)

<<<<<<< HEAD
=======
/**
  * 继承多个特质用with关键字
  * @param name
  */
>>>>>>> 重新拉取repo
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
<<<<<<< HEAD
=======
}

class Cat(val name: String) extends Animal

/**
  * 除了可以给未实例化的类混入特质，还可以给实例化的类混入特质
  */
object Main1 extends App {
  val angel = new Cat("Angel") with Friend
  angel.listen
>>>>>>> 重新拉取repo
}