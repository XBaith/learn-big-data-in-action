package scala.pragmatic.set

import java.util.NoSuchElementException

/**
  * 键值对字典
  */
object MapDemo {
  def main(args: Array[String]): Unit = {
    val books = Map("MySQL必知必会" -> 34, "HBase原理与实战" -> 100, "基于Flink的流处理" -> 80, "Flink简明教程" -> 50)

    /*get()由键得值，返回Option[A]类型，因为有值为空的可能*/
    println(s"基于Flink的流处理的价格: ${books.get("基于Flink的流处理").get}")
    /*apply()与get()不同的是返回的直接是值，会出现空异常*/
    println(s"基于Flink的流处理的价格: ${books("基于Flink的流处理")}")
    try {
      println(s"基于Flink的流处理的价格: ${books("Flink")}")
    } catch {
      case _: NoSuchElementException => println("Not found")
    }

    /*用过滤器查找map中的内容*/
    val bbs = books.filterKeys(e => e.contains("Flink"))
    bbs.foreach(e => println(e._1 + " : " + e._2))

    val bs = books.filter{
      element => val (k, v) = element
        k.contains("Flink")
    }
    bs.foreach(e => println(e._1 + " : " + e._2))

    /*updated()更新Map，不可变集返回一个新的Map*/
    val books1 = books.updated("Hadoop权威指南", 100)
    println(s"更新后的书本数: " + books1.size)

  }
}
