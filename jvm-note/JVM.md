# JVM
 
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

## 第9章 类加载及执行子系统的案例与实战
