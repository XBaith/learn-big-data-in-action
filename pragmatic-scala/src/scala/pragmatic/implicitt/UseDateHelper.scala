package scala.pragmatic.implicitt
import scala.pragmatic.implicitt.DateHelper._

object UseDateHelper extends App {
  val agoDate = 2 days ago
  val afterDate = 5 days from_now

  println(s"age: $agoDate\nfrom_now: $afterDate")

  val agoMonth = 5 months ago
  val afterMonth = 12 months from_now
  println(s"age: $agoMonth\nfrom_now: $afterMonth")
}
