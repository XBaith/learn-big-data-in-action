# Scala函数式编程

## 高阶函数
- 定义：将其他函数作为参数的函数成为高阶函数

例如：

```
  /**
    * 按某个函数来求一个区间的和
    * @param left　下界
    * @param right  上界
    * @param code　函数
    * @return 整型值
    */
  def totalResult(left: Int, right: Int, code: Int => Int) : Int = {
    var res = 0
    for( i <- left to right ) {
      res += code(i)
    }
    res
  }
```

其中totalResult中包含一个参数函数code，=>的左右侧分别为其函数的输入类型与输出返回类型，那么如果求1到100的整数之和就可以写为：

```
totalResult(1, 100, n => n)
```

## 柯里化（currying）

可以把接受多个参数的函数转化为接受多个参数列表的函数，例如：

```
def foo(a: Int, b: Int, c: Int) = {}
```

可以转化为有多个参数列表的

```
def foo(a: Int)(b: Int)(c: Int) = {}
```

这样可以使用柯里化将函数值从括号中拿出来，放在的大括号中来代替。

可以这么调用foo函数：`foo(1)(2){3}`或者`foo(1){2}{3}`等

