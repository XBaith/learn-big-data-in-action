package scala.hello.implicitt

object DateUilt {
  val ago = "ago"
  val from_now = "from_now"

  implicit class DateHelper(val offset: Int){
    import java.time.LocalDate
    val today = LocalDate.now
    def days(when: String): LocalDate = {
      when match {
        case ago => today.minusDays(offset)
        case from_now => today.plusDays(offset)
        case _ => today
      }
    }
  }
}
