package partone.datastruct

/**
  * 符号'+'代表支持协变
  * 例如X时Y的子类，则List[X]也是List[Y]的子类
  */
sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[A](head: A, tail: List[A]) extends List[A]

/**
  * List类的伴生对象（compantion）
  * 在其中定义List类的方法等
  */
object List {
  //求和
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def tail[A](as: List[A]): List[A] = as match {
    case Nil => Nil
    case Cons(h, t) => t
  }

  def setHead[A](newHead: A, as: List[A]) : List[A] = {
    as match {
      case Nil => Nil
      case Cons(head, tail) => Cons(newHead, tail)
    }
  }


  //用可变参数，在初始化时可以通过List(1,2,3)等方式传递多个参数来创建实例，只需要用','隔开就行
  def apply[A](as: A*): List[A] = {
    if (as.isEmpty) Nil else Cons(as.head, apply(as.tail: _*))
  }
}

object Main extends App {

  import List._

  //有多种情况可以匹配时，最先匹配的为最终结果
  val x = List(1, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }
  println(x)

  val list = List(1,2,3,4,5)
  println(tail(list))
  println(setHead(10,list))
}