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

<<<<<<< HEAD
    println("中途绑定mid-stream")
    grepMidStream(".*gcd.*")

    println("得到新的Array集合")
    grepMidStream(".*for.*")
=======
    println("中途绑定(mid-stream)")
    grepMidStream(".*gcd.*")

    println("得到新的Array集合")
    val set = grepGetArray(".*gcd.*")
    for(length <- set)
      print(length + "\t")

    println()
    /*continue代替方案*/
    for(i <- 1 to 10 if (i != 0 && i != 5)) {
      print(i + " ")
    }
>>>>>>> 重新拉取repo
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
<<<<<<< HEAD
      file
      <- files
      if file.getName().endsWith(".scala")
      line <- fileLines(file)
      if line.trim.matches(pattern)
=======
      file <- files if file.getName().endsWith(".scala")
      line <- fileLines(file) if line.trim.matches(pattern)
>>>>>>> 重新拉取repo
    } println(file + "　:　" + line.trim)

  def grepMidStream(pattern: String) =
    for {
<<<<<<< HEAD
      file <- files
      if file.getName().endsWith(".scala");
      line <- fileLines(file)
      trimmed = line.trim
=======
      file <- files if file.getName().endsWith(".scala");
      line <- fileLines(file)
      trimmed = line.trim //中途绑定的就是trimmed变量，仅且只能有一个
>>>>>>> 重新拉取repo
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
