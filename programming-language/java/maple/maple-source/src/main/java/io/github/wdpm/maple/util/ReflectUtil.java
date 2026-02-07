package io.github.wdpm.maple.util;

import java.lang.reflect.Method;

/**
 * @author evan
 * @date 2020/4/22
 */
public class ReflectUtil {

    public static Object newInstance(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Object invokeMethod(Object object, Method method, Object... args) {
        try {
            Class<?>[] types    = method.getParameterTypes();
            int        argCount = args == null ? 0 : args.length;

            // method: users
            // args: [io.github.wdpm.maple.servlet.wrapper.Request@1b7d51ab, io.github.wdpm.maple.servlet.wrapper.Response@328eef8b]
            // types.length: 2

            // 检查参数个数是否一致
            ExceptionUtil.makeRunTimeWhen(argCount != types.length, "%s in %s", method.getName(), object);

            // 转参数类型
            for (int i = 0; i < argCount; i++) {
                args[i] = cast(args[i], types[i]);
            }

            return method.invoke(object, args);
        } catch (Exception e) {
            ExceptionUtil.makeRuntime(e);
        }
        return null;
    }

    /**
     * 将入参Object类型的value转换为更为具体的类型，例如integer,String
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object value, Class<T> type) {
        if (value != null && !type.isAssignableFrom(value.getClass())) {
            if (is(type, int.class, Integer.class)) {
                value = Integer.parseInt(String.valueOf(value));
            } else if (is(type, long.class, Long.class)) {
                value = Long.parseLong(String.valueOf(value));
            } else if (is(type, float.class, Float.class)) {
                value = Float.parseFloat(String.valueOf(value));
            } else if (is(type, double.class, Double.class)) {
                value = Double.parseDouble(String.valueOf(value));
            } else if (is(type, boolean.class, Boolean.class)) {
                value = Boolean.parseBoolean(String.valueOf(value));
            } else if (is(type, String.class)) {
                value = String.valueOf(value);
            }
        }
        return (T) value;
    }


    /**
     * 检测入参type是否为classes中的一个
     *
     * @param type    特定类型
     * @param classes 类型搜索范围
     * @return 如果属于范围，则返回true，否则false
     */
    public static boolean is(Object type, Object... classes) {
        if (type != null && classes != null) {
            for (Object cls : classes)
                if (type.equals(cls))
                    return true;
        }
        return false;
    }

}
