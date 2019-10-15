package scala.pragmatic.implicitt

import scala.language.implicitConversions
import java.time.LocalDate

class DateHelper(offset: Int) {
  def days(when: String): LocalDate = {
    val today = LocalDate.now
    //模式比配
    when match {
      case "ago" => today.minusDays(offset)
      case "from_now" => today.plusDays(offset)
      case _ => today
    }
  }

  def months(when: String): LocalDate = {
    val today = LocalDate.now
    //模式比配
    when match {
      case "ago" => today.minusMonths(offset)
      case "from_now" => today.plusMonths(offset)
      case _ => today
    }
  }
}
//伴生对象
object DateHelper {
  val ago = "ago"
  val from_now = "from_now"
  //隐式函数，把Int类型转为Date类型
  implicit def convertInt2DateHelper(offset: Int): DateHelper = new DateHelper(offset)
}
