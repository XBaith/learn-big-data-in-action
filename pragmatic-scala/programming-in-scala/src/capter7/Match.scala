package capter7

object Match {
  def main(args: Array[String]): Unit = {
    val firstArg = if(!args.isEmpty) args(0) else ""
    val result = firstArg match {
        case "hello" => "scala"
        case "fuck" => "that"
        case _ => "wtf?"
      }
    println(result)
  }
}
