package annotation;

/**
 * @author xunle
 * @since 2024/6/21 18:55
 */

public class FooImpl implements Foo {
    String name;

    public FooImpl(String name) {
        this.name = name;
    }

    @Override
    @Logger
    public void display() {
        System.out.println("foo name: " + name);
    }
}
