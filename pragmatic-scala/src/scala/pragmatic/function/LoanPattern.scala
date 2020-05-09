package scala.pragmatic.function

import java.io.{File, PrintWriter}

/**
  * 资源密集型对象借来后，使用完毕之后就归还
  */
object LoanPattern {
  def main(args: Array[String]): Unit = {
    val path = "output.txt"
    def writeOp = {writer: PrintWriter => writer.println("write something...")}
    writeToFile(path){writeOp}
  }

  def writeToFile(file: String) (writeOp: PrintWriter => Unit) = {
    val writer = new PrintWriter(new File(file))
    try {
      writeOp(writer)
    }finally {
      writer.close()
    }
  }
}
