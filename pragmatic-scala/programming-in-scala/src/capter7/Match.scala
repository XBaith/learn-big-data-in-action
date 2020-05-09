package capter7

object Match {
  def main(args: Array[String]): Unit = {
    val firstArg = if(!args.isEmpty) args(0) else ""
<<<<<<< HEAD
    val result =
      firstArg match {
=======
    val result = firstArg match {
>>>>>>> 重新拉取repo
        case "hello" => "scala"
        case "fuck" => "that"
        case _ => "wtf?"
      }
<<<<<<< HEAD

=======
>>>>>>> 重新拉取repo
    println(result)
  }
}
