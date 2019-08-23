package scala.hello.types

import java.util

object AnyType {
  var num : Int = 0
  var any : Any = _
  //OK
  any = num
  //error
  //num = any

  var numList = new util.ArrayList[Int]
  var anyList = new util.ArrayList[Any]
  //ERROR
//  numList = anyList
  //ERROR
//  anyList = numList
}
