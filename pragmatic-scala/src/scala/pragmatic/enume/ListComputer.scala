package scala.pragmatic.enume

object ListComputer extends App {
  Computers.values.foreach{
    (computer => println(computer))
  }
}
