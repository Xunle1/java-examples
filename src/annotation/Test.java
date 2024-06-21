package annotation;

import java.lang.reflect.Proxy;

/**
 * @author xunle
 * @since 2024/6/21 19:28
 */
public class Test {

    public static void main(String[] args) {
        Foo target = new FooImpl("bar");
        LoggerHandler handler = new LoggerHandler(target);

        Foo foo = (Foo) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
        foo.display();
    }
}
