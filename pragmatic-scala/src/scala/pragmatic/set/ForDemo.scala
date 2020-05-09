package scala.pragmatic.set

class Student(val fistName: String, val lastName: String)
object Student {
  def apply(fistName: String, lastName: String): Student = new Student(fistName, lastName)
}

object ForDemo extends App {
  val stus = List(Student("王", "小龙"), Student("张", "王琦"), Student("孙", "晨晨"))
  val lastNames = for(stu <- stus) yield stu.lastName
  println(lastNames)
}
