package scala.pragmatic.mytrait

<<<<<<< HEAD
object DecoratorPattern {

=======
abstract class Check {
  def check: String = s"Check application..."
}

trait CriminalRecordCheck extends Check {
  override def check: String = s"Checked criminal record... ${super.check}"
}

trait EmploymentCheck extends Check {
  override def check: String = s"Checked employment record... ${super.check}"
}

/**
  * 特质的方法调用链是从最后一个混入的特质开始,最后到基类方法
  * 最后混入的特质具有拦截方法调用的最高优先级
  */
object DecoratorPattern {
  def main(args: Array[String]): Unit = {
    val employment = new Check with CriminalRecordCheck with EmploymentCheck
    println(employment.check)
  }
>>>>>>> 重新拉取repo
}
