package methodcall;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleDemo {

    static class ClassA{
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        getPrintMH(obj).invokeExact("Method Handle Test");
    }

    private static MethodHandle getPrintMH(Object reveiver) throws NoSuchMethodException, IllegalAccessException {
        /*第一个参数是返回参数，第二个以后是方法接受的参数*/
        MethodType methodType = MethodType.methodType(void.class, String.class);
        /*lookup()根据接受对象，方法名，函数类型来找到方法句柄；bindTo()指定方法的接受者；findVirtual对应的字节码是invokevirtual，执行虚方法*/
        return MethodHandles.lookup().findVirtual(reveiver.getClass(), "println", methodType).bindTo(reveiver);
    }
}
