package scala.hello.enume

object ListComputer extends App {
  Computers.values.foreach{
    (computer => println(computer))
  }
}
