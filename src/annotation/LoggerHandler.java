package annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author xunle
 * @since 2024/6/21 20:09
 */
public class LoggerHandler implements InvocationHandler {

    private final Object target;

    public LoggerHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o;
        Method invokeMethod = method;
        if (!invokeMethod.isAnnotationPresent(Logger.class)) {
            invokeMethod = target.getClass().getMethod(method.getName());
            if (!invokeMethod.isAnnotationPresent(Logger.class)) {
                return invokeMethod.invoke(target, args);
            }
        }

        Logger logger = invokeMethod.getAnnotation(Logger.class);
        TimeUnit unit = logger.unit();
        long start = System.currentTimeMillis();
        o = invokeMethod.invoke(target, args);
        long end = System.currentTimeMillis();
        long cost = end - start;

        switch (unit) {
            case MILLISECONDS ->
                    System.out.println(invokeMethod.getName() + " cost: " + cost + "ms");
            case SECONDS ->
                    System.out.println(invokeMethod.getName() + ": cost: " + cost / 1000 + "s");
            case MINUTES -> System.out.println(invokeMethod.getName() + ": cost: " + cost / 60000 +
                    "min");
            case HOURS -> System.out.println(invokeMethod.getName() + ": cost: " + cost / 3600000 +
                    "h");
            case DAYS ->
                    System.out.println(invokeMethod.getName() + ": cost: " + cost / 86400 + "d");
            case NANOSECONDS ->
                    System.out.println(invokeMethod.getName() + ": cost: " + cost * 1000 + "ns");
            case MICROSECONDS ->
                    System.out.println(invokeMethod.getName() + ": cost: " + cost * 100000 + "µ");
        }
        return o;
    }

}
