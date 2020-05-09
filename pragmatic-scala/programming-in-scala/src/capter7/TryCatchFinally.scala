package capter7

import java.io.{File, FileReader}
import java.net.{MalformedURLException, URL}

object TryCatchFinally {

  def even(n: Int): Int = {
    if(n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even")
  }

  def urlFor(path: String) =
    try{
      new URL(path)
    }catch {
      case e: MalformedURLException =>
        new URL("https://github.com/XBaith")
    }

  def main(args: Array[String]): Unit = {

    val file = new FileReader("capter7/build-in-control-structure.md")
    try {
      //使用文件
    }finally {
      //关闭文件
      file.close()
    }

  }
}
