object FindLongLines {
  def main(args: Array[String]) : Unit = {
    val width = args(0).toInt
    for(file <- args.drop(1))
      LongLines.processFile(file, width)
  }
}
