package rational

class Rational(n: Int, d: Int) {
  require(d != 0)/*前置条件除数不能为0*/
  override def toString: String = n + " / " + d /*重写toString方法*/

  val number = n  /*用常量表示出来，后面就可以访问构造函数中的n与d，如果不写出字段就访问不到类中的d与n*/
  val damon = d
  def +(that: Rational) = new Rational(number * that.damon + that.number * damon, damon * that.damon)
  def -(that: Rational) = new Rational(number * that.damon - that.number * damon, damon * that.damon)

  def *(that: Rational) = new Rational(number * that.number, damon * that.damon)
  def /(that: Rational) = new Rational(number * that.damon, damon * that.number)
}

object Main extends App {
  val left = new Rational(2, 3)
  val right = new Rational(1, 5)
  //同分后：10/15, 3/15
  println(left + right)
  println(left - right)
  println(left * right)
  println(left / right)
}
