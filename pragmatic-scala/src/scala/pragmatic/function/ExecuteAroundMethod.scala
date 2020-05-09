package scala.pragmatic.function

object ExecuteAroundMethod {
<<<<<<< HEAD

=======
  def main(args: Array[String]): Unit = {
    Resource.use(res => {
      res.op1()
      res.op2()
      res.op3()
    })
  }
}

class Resource private () {
  println("Starting...")
  private def clean() = println("Cleaning and Ending...")
  def op1() = println("OP1")
  def op2() = println("OP2")
  def op3() = println("OP3")
}

object Resource {
  def use(code: Resource => Unit) = {
    val resource = new Resource
    try {
      code(resource)
    }finally {
      resource.clean()
    }
  }
>>>>>>> 重新拉取repo
}
