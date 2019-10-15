package rational

/**
 * 有理数
 * @param n
 * @param d
 */
class Rational(n: Int, d: Int) {
  require(d != 0)   /*前置条件除数不能为0*/

  def this(n: Int) = this(n, 1)   /*辅助构造方法*/

  private val g = gcd(n.abs, d.abs)
  val number = n / g    /*用常量表示出来，后面就可以访问构造函数中的n与d，如果不写出字段就访问不到类中的d与n*/
  val damon = d / g

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def +(that: Rational) =
    new Rational(number * that.damon + that.number * damon, damon * that.damon)
  def -(that: Rational) =
    new Rational(number * that.damon - that.number * damon, damon * that.damon)
  def *(that: Rational) =
    new Rational(number * that.number, damon * that.damon)
  def /(that: Rational) =
    new Rational(number * that.damon, damon * that.number)
  def min(that: Rational): Rational =
    if(this.number * that.damon < that.number * this.damon) this else that
  def max(that: Rational):Rational =
    if(this.number * that.damon > that.number * this.damon) this else that

  /*方法重载，让有理数类支持与Int类型运算*/
  def +(n: Int):Rational =
    new Rational(number + n * damon, damon)
  def -(n: Int):Rational =
    new Rational(number - n * damon, damon)
  def *(n: Int):Rational =
    new Rational(number * n, damon)
  def /(n: Int):Rational =
    new Rational(number, damon * n)

  override def toString: String = number + " / " + damon   /*重写toString方法*/
}

object Main extends App {
  implicit def intToRational(n: Int) = new Rational(n)    /*隐式转换*/

  print(2 + new Rational(3, 1))
}
