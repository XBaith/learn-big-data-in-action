# Scala包

## Scala中自动引入的包

### 自动引入的常用包

- **java.lang.***

- **scala**，但不包括scala中的全部子包
- **Predef**，其中包含print, println, equals, hashCode, toString等函数



### 包的表达

scala除了java声明包的方式外，还支持在一个文件中创建多个包以及给各个包创建类，特质Trait或者object

例如：

```
package chapter07 {
  package scala.mypackage {

    object MyPackage {
      def main(args: Array[String]): Unit = {
        println(this.getClass)
      }
    }

  }
}
```

那么编译器就会创建chapter07.scala.mypackage的包，其中就包含编译后的class文件，MyPackage.class和MyPackage$.class。

- 包中可以嵌套包，在同一个文件中就可以创建不同包中的class/trait/object 

### 包的作用域

- 同一文件下，子包可以直接引用父包，不用**import**导入

- 父包要访问子包的内容时，必须要**import**

