package scala.pragmatic.function

import java.util.Date

object 部分应用函数 {
  def main(args: Array[String]): Unit = {
    val date = new Date
    log(date, "Not bound date message")

    val logBoundDate = log(date, _) //是一个函数引用，其中用下划线占位符来表示另一个参数，绑定date
    logBoundDate("Bounded message1")
    logBoundDate("Bounded message2")
  }

  def log(date: Date, msg: String) = {
    println(date + ", Message: " + msg)
  }
}
