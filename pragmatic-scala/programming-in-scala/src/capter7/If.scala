package capter7

object If {
  def main(args: Array[String]): Unit = {
    println(if(!args.isEmpty) args(0) else "default.txt")
  }
}

