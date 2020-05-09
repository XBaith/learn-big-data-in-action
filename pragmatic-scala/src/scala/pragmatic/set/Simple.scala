package scala.pragmatic.set

class Simple {
  def unary_+() : Unit = println("called unary + ")
}

object M extends App {
  val simple = new Simple
  + simple
}
