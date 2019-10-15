package scala.pragmatic.implicitt

/**
  * 值类特点：一般情况下可以消除实例化，也能在代码中创建更好的抽象
  * 不能消除实例化：1)当任何除了继承自底层原始类型之外的类型引用值类时，值类就会被实例化。
  * 2)如果赋值给数组，或Depends on its runtime type information to make the decision也会创建值类实例
  */
object DateUtils {
  val ago = "ago"
  val from_now = "from_now"
//隐式类，不需要import implicitConversions，因为容易辨认
  implicit class DateHelper(val offset: Int) extends AnyVal {
    import java.time.LocalDate
// DateHelper继承了AnyVal后消除了调用隐式转换时创建隐式类实例的开销，减少垃圾。此时变为值类(value classes)
// 注意：在隐式类中不允许有定义的字段，因此today被移到days函数中
    def days(when: String): LocalDate = {
      val today = LocalDate.now
      when match {
        case "ago" => today.minusDays(offset)
        case "from_now" => today.plusDays(offset)
        case _ => today
      }
    }
  }
}
