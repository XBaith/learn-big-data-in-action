# JVM

[TOC]

java8与java7的JVM模型区别：https://www.jianshu.com/p/a79b3174c2fb

## 第8章 虚拟机字节码执行引擎

### 3. 方法调用

#### <font color="#2E86C1">动态类型语言支持</font>
MethodHandle可以看做方法的“引用”，**通过调用invoke*四种方法字节码指令，将如何查找目标方法的决定权交从虚拟机交给用户完成。**

- <font color="#E74C3C">MethodHandle与Reflection反射的区别</font>
1. Reflection是模拟Java代码层次的方法调用，MethodHandle是模拟字节码层次的调用。MethodHandles.lookup()中包含findStatic(),findVirtual(),findSpecial()分别对应了invokestatic,invokevirtual,invokespecial字节码指令。
2. Reflection比MethodHandle中包含的信息多。因此Reflecion是重量级的，而MethodHandle是轻量级的。
3. MethodHandle是字节码的方法指令调用，因此虚拟机可以做各种优化。
4. Reflection只是对Java适用，而MethodHandle是服务于JVM上的语言。

- invokedynamic与前四条"invoke*"指令的区别是分派逻辑是由程序员决定的。

- 方法分派规则
掌握方法分派规则可以改变方法分派规则，例如孙子类可以调用爷爷类的函数

### 4. 基于栈的字节码解释执行引擎
#### <font color="#2E86C1">解释执行</font>
Java代码是解释执行还是编译执行，取决于具体实现的虚拟机（实现版本和执行引擎的运行模式）

#### <font color="#2E86C1">基于栈的指令集与基于寄存器的指令集</font>
Java采用基于栈的指令集(虚拟机)，实现简单，代码紧凑，但是运行速度比基于寄存器的指令集慢，虽然Java会经常访问的操作映射到寄存器中，但还是频繁访问内存带来的开销导致速度较慢。

#### <font color="#2E86C1">基于栈的解释执行过程</font>
例如下列计算过程，用javap查看编译后的字节码
```
public int calc() {
    int a = 100;
    int b = 200;
    int c = 300;
    return (a + b) * c;
}
```
```
public static int calc();
    descriptor: ()I
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=0
         0: bipush        100
         2: istore_0
         3: sipush        200
         6: istore_1
         7: sipush        300
        10: istore_2
        11: iload_0
        12: iload_1
        13: iadd
        14: iload_2
        15: imul
        16: ireturn
      LineNumberTable:
        line 6: 0
        line 7: 3
        line 8: 7
        line 9: 11
}
```
- stack=2 : 需要2个深度的操作数栈
- locals=3 : 需要三个Slot局部变量空间

首先将a变量推入操作数栈顶(bipush)，在把操作栈中的变量放到局部变量表的Slot 0中(istore_0)，到11之前就a,b,c变量都放到局部变量表之中；之后再把局部变量表中的数加载到操作栈里(iload_x)，先把100和200相加(iadd)再把Slot2中的300加载到操作栈(iload_2)，然后乘指令得出结果(imul)后返回(ireturn)。

<font color="#F39C12">总结：先把变量值用bipush压入操作栈中，再把通过istore_n指令操作栈的内容放到局部变量表Slot n 中，单位是Slot；最后把局部变量表中的值压入栈并进行运算得出结果。</font>

## 第10章 早期（编译器）优化

### 1. 概述

- 前端编译器：将.java文件转变成.class文件。Sun的Javac
- JIT编译器：将字节码转变为机器码。HotSpot VM的C1,C2编译器
- AOT编译器：直接把.java文件转变为机器码。GNU Compiler for the Java(GCJ)

### 2. Javac

#### <font color="#2E86C1">编译过程</font>

1. 解析与填充字符表
2. 注解处理
3. 解析与字节码生成

##### (1) 解析与填充符号表

###### 1) 词法，语法分析

**词法分析**是将源代码的字符流转变为标记(Token)集合。单个字符是编程过程的最小元素，而标记是编译过程的最小元素。例如`int a = b + 2`，含有六个Token。

**语法分析**是根据Token序列构造抽象语法树(Abstract Synatax Tree)的过程。AST可以用树形来描述程序代码语法结构，语法树中每个节点代表一个语法结构，如包，类型，修饰符，运算符，接口甚至代码注释。

###### 2) 填充符号表

**符号表**是由一组符号地址和符号信息构成的表格。用于语义检查，对符号名进行地址分配等。



##### (2) 注解处理器

在运行期间发挥作用，如果处理注释期间修改了语法树，那么编译器将退回到上一步——解析和填充符号表。

##### (3) 语义与字节码生成

语法分析不能保证源程序逻辑正确，而**语义分析**是对结构上正确的源程序进行上下文有关性质的检查，如：int类型不能和char类型做加法。语义分析包含标注检查，数据及控制流分析。

###### 1) 标注检查

检查变量使用前是否被声明，变量和赋值之间的数据类型是否匹配等。

###### 2) 数据及控制流分析

对程序上下文逻辑进一步验证，检查程序局部变量是否在使用前被赋值，方法每条路径是否有返回值，是否所有受查异常都被正确处理等。

在局部变量声明的修饰符与没有修饰符是一样的，因为局部变量不存在CONSTANT_Fieldref_info，也就不存在Access_Flags。

###### 3) 解语法糖

**语法糖**是指对程序功能没有影响，但是方便程序员使用的某种语法。

###### 4) 字节码生成 

字节码生成阶段作用是：将转换好的字节码文件写入磁盘与添加了少量的代码和转换工作，例如<init>()和<clinit>()。



### 3. Java语法糖

#### 泛型擦除

- 真泛型：在源代码，编译后或者其他阶段都是存在的，有着自己的虚方法表和类型数据。这种实现称为类型膨胀。
- 假泛型：只在程序源码中存在，编译后的字节码文件还是原生类型，并在相应的地方插入强制转换。也就是说ArrayList<int>和ArrayList<String>是同一个类。因此Java的泛型属于语法糖

当泛型遇到重载：重载要求不同的特征签名，Java的特征签名不包含返回类型，但是如果同一个方法参数使用到了泛型，而返回值不一样时，按照重载的要求，返回值不同不能作为重载的条件。但由于返回值不同，Java的字节码文件Code属性存在差异，某些编译器还是允许重载的（Sun jdk 1.6的Javac编译器，其他不一定）。

在Signature属性中保存一个方法在字节码层面的特征签名，包含了参数化类型的信息。可以得出泛型擦除仅仅是对Code属性中的字节码擦除，元数据还是保存着泛型信息，这也是反射得到参数化类型的依据。

#### 自动装箱，拆箱与遍历循环

```
List<Integer> list = Arrays.asList(1, 2, 3, 4);
for(int i : list){
    
}
```

等价于

```
List list = Arrays.asList(new Integer[]{
    Integer.valueof(1),
    Integer.valueof(2),
    Integer.valueof(3),
    Integer.valueof(4)
})
for(Iterator localIterator = list.iterator(); localIterator.hasNext(); ) {
    int i = ((Integer)localIterator.next()).intValue();
}
```

#### 条件编译

> —般情况下,[C语言](https://baike.baidu.com/item/C%E8%AF%AD%E8%A8%80)[源程序](https://baike.baidu.com/item/%E6%BA%90%E7%A8%8B%E5%BA%8F/9752646)中的每一行代码.都要参加编译。但有时候出于对程序代码优化的考虑.希望只对其中一部分内容进行编译.此时就需要在程序中加上条件，让编译器只对满足条件的代码进行编译，将不满足条件的代码舍弃，这就是条件编译（conditional compile）。——百度百科

Java中直接通过if条件语句做到条件编译，例如：

```
public static void main(String[] args) {
    if(true)　{
        System.out.println("block1");
    } else {
        System.out.println("block2");
    }
}
```

经过编译器优化后就变成

```
public static void main(String[] args) {
    System.out.println("block1");
}
```



## 第11章　晚期（运行期）优化

### 1. 概述

当虚拟机发现否个方法或代码块运行的特别频繁时，就会认定这段代码为”热点代码“，此时，虚拟机会将”热点代码“编译为本地平台相关的机器码，用来提高效率。交给Just In Time Compiler(JIT)即时编译器来完成。

代码优化是建立在某种中间表示(HIR, LIR)或者机器码的层面，绝不是源码之上。

### 2. HotSpot虚拟机内的JIT

#### (1) 解释器与编译器

