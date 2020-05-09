package scala.pragmatic.set

<<<<<<< HEAD
object SetDemo {

=======
/**
  * 无序的集合
  */
object SetDemo {
  def main(args: Array[String]): Unit = {
    val employee = ("老明", "小王", "小李")
    println(s"${employee}")

    val employee1 = employee + "小孙"
    println(s"${employee}, ${employee1}")

    val employee2 = employee1 + "小黄"
    println(s"${employee}, ${employee1}, ${employee2}")

    import scala.collection.mutable.Set
    val emps = Set("老明", "小王", "小李")
    println(s"${emps}")
    emps.add("小孙")
    println(s"添加后的员工集合: ${emps.mkString(", ")}")

    /*查看Set包含某字符串的元素集合*/
    println(s"名字中带有小的员工有: ${emps.filter(e => e.contains("小")).mkString(", ")}")
    val emps1 = Set("小孙", "小王", "小黄", "小李")
    /*并集　用++()方法*/
    val unionEmps = emps ++ emps1
    println(s"emps和emp1所有的员工有: ${unionEmps.mkString(", ")}")
    /*交集　用&()方法*/
    val mixEmps = emps & emps1
    println(s"emps和emp1共有的员工有: ${mixEmps.mkString(", ")}")

    /*给集合中的每个元素前都加入前缀　用map()*/
    val preEmps = emps.map("员工:" + _)
    println(preEmps.mkString(", "))

    /*迭代集合元素 foreach*/
    emps.foreach( employee => println("查看员工:" + employee))
  }
>>>>>>> 重新拉取repo
}
