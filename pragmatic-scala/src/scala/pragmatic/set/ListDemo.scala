package scala.pragmatic.set

/**
  * 有序的对象集合
  */
object ListDemo {
  def main(args: Array[String]): Unit = {
    val list = List("小孙", "小王", "小黄", "小李")
    /*头部和隐式调用list() = list.apply()*/
    println(list.head)
    println(list(0))
    /*链表的尾部是一个List*/
    println(list.tail)
    /*最后一个元素*/
    println(list.last)

    /*在List前插入一个元素，用 ::()，因为是不可变List所以添加元素后的链表也是一个新的List*/
    println("小前" :: list)

    /*在List前插入另一个List*/
    val list1 = List("老钱")
    println(list1 :::list)
    println(list ::: list1)
  }
}
