package scala.pragmatic.implicitt

object UseDateUtil extends App {
  import DateUtils._
  val agoDate = 2 days ago
  val afterDate = 10 days from_now

  println(s"age: $agoDate\nfrom_now: $afterDate")
}
