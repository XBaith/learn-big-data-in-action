package scala.pragmatic.implicitt

object Mask extends App {
  import MaskInterpolator._
  val ssn = "123-45-6789"
  val account = "account-root"
  val balance = 20123.12

<<<<<<< HEAD
  println(mask"""Account: $account
  |Social Security Number: $ssn
  |Balance: $$^$balance
  |Thanks for you bussiness.""".stripMargin)
=======
/*  println(mask"""Account: $account
  |Social Security Number: $ssn
  |Balance: $$^$balance
  |Thanks for you bussiness.""".stripMargin)*/
>>>>>>> 重新拉取repo
}
