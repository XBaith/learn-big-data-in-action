# 内建的控制结构
包括:

- if
- while
- for
- try
- match

在Scala中不只if会返回一个数值，for,try和match也会返回数值，这样可以节省为了得到结果在控制结构外创建的临时变量

## if表达式
```
val filename = "default.txt"
if(!args.isEmpty())
    filename = args(0)
```
由于if可以有返回值，所以可以写成
```
val filename = 
    if(!args.isEmpty) args(0) 
    else "default.txt"
```

## while表达式
while循环返回的是一个Unit值，与Java的void不同的是，Scala对Unit可以进行判断，如
```
#sbt
def greet = println("hi")
() == greet()
```
对于
```
while((line = readLine()) != "")
```
是不可行的，因为Unit与String类型作比较永远是ture,在Java中赋值语句结果就是被赋上的值，而在Scala中赋值操作返回的是()也就是Unit，因此在Scala中，这样的操作是不可行的。

如果用while那么应该有var的变量配合使用，但提倡不用while和var
## for表达式
for功能有：
### 1.遍历集合
for可以用于遍历任何形式的集合
语法：
```
for(n <- List)
```
每次遍历都会创建一个val n的变量

特别的是Range，可以用`1 to 4`来代表从１到4的整数，`1 until 4`代表[1,4)的整数
```
for(n <- 1 to 4)
```
### 2.过滤
可以在for循环中或者循环内加入if语句当做过滤器

### 3.嵌套迭代
可以在for语句中如果多个<-来嵌套迭代的遍历，在()内在一层迭代完成后要加';'，因为()中不能自动补全分号，
可以用花括号来代替圆括号

### 4.中途变量绑定
在for语句中可以中途绑定一个变量，在for语句括号内可以直接引用

### 5.产生一个新集合
上述4种方法for循环返回的都是Unit，如果要返回一个新的集合可以
在for语句和代码块之前用**yield**关键字，可以将for循环中遍历的变量存入一个集合中

语法
```
for 子句　yield 代码体
```

## try表达式
用try表达式可以实现异常处理

Scala中异常throw语句返回的是Nothing类型

try-catch-finally 中 当出现异常需要捕捉时，catch代码块中需要有case... => ...来做模式匹配。

finally代码块中不要有return，因为
```
try {return 1}
finally {return 2} 
```
返回值为２
```
try 1
finally 2
```
返回值是１。finally块中建议是一些避免副作用的操作，例如关闭文件流

## match表达式
match用法与switch差不多，都是从多个选项中进行选择，但是Java中的switch只能判断整形，字符串常量，枚举，而Scala中的case能够判断任何类型；并且case中隐含break；最重要的是match匹配后可以返回值。

## 代替方案

1. 因为Scala中没有break和continue关键字，所以在循环中需要用嵌套if代替continue,布尔变量代替break。

   - 另外，Scala中还提供一种高阶函数（可以将函数作为参数的函数）来处理break()（利用抛出异常来达到跳出循环的效果），breakable(op: => Unit)，即是将一个没有返回值且没有参数的函数处理，源码如下：

   ```
   	
   ```

   ​	这样在处理break()之后就可以继续执行后续的代码，否则就会直接抛出异常。

   - 对于continue，可以在for循环中加入过滤条件(if)，例如如下就可以跳过第0和第5

     ```
     for (i <- 0 to 10 if (i != 0 && i != 5))
     ```

     

2. 用递归代替while循环与var

   ```
   var i = 0
   car flag = false
   while(i < args.length && ! flag) {
       if(!args(i).startWith("-"))
       	if(args(i).endWith(".scala")) flag = true
       i = i + 1
   }
   ```

   代替为

   ```
   def searchFrom(i : Int) : Int {
   	if(args(i).startWith("-"))	searchFrom(i + 1)
   	else if(args(i).endWith(".scala"))	i
   	else searchFrom(i + 1)
   }
   
   val i = searchFrom(0)
   ```
