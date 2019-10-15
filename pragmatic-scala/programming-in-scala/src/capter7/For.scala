package capter7

import java.io.File

object For {
  val files = new File(("./programming-in-scala/src/capter7")).listFiles()

  def main(args: Array[String]): Unit = {
    println("遍历")
    for(file <- files)
      println(file)

    println("过滤")
    /*在for语句中插入多个if过滤器*/
    for(file <- files if file.getName().endsWith(".scala") if file.isFile())
      println(file)

    println("嵌套迭代")
    grep(".*gcd.*")

    println("中途绑定mid-stream")
    grepMidStream(".*gcd.*")

    println("得到新的Array集合")
    grepMidStream(".*for.*")
  }

  /*嵌套迭代*/
  def fileLines(file: File) =
    scala.io.Source.fromFile(file).getLines().toList

  def grep(pattern: String) =
    for(
      file <- files
      if file.getName().endsWith(".scala");
      line <- fileLines(file)
      if line.trim.matches(pattern)
    ) println(file + "　:　" + line.trim)

  def grepReplaced(pattern: String) =
    for{
      file
      <- files
      if file.getName().endsWith(".scala")
      line <- fileLines(file)
      if line.trim.matches(pattern)
    } println(file + "　:　" + line.trim)

  def grepMidStream(pattern: String) =
    for {
      file <- files
      if file.getName().endsWith(".scala");
      line <- fileLines(file)
      trimmed = line.trim
      if trimmed.matches(pattern)
    } println(file + "　:　" + trimmed)

  def grepGetArray(pattern: String) =
    for {
      file <- files
      if file.getName().endsWith(".scala");
      line <- fileLines(file)
      trimmed = line.trim
      if trimmed.matches(pattern)
    } yield trimmed.length

}
