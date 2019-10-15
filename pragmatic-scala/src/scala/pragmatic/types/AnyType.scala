package scala.pragmatic.types

import java.util

object AnyType {
  var num : Int = 0
  var any : Any = _

  any = num //OK

  //num = any //error

  var numList = new util.ArrayList[Int]
  var anyList = new util.ArrayList[Any]
  //ERROR
//  numList = anyList
  //ERROR
//  anyList = numList
}
