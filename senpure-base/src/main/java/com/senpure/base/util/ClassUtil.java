package com.senpure.base.util;

import java.lang.reflect.Method;

/**
 * ClassUtil
 *
 * @author senpure
 * @time 2020-10-16 16:09:36
 */
public class ClassUtil {

    public static Method getMethodUpInTurn(Class<?> clazz, String name, Class<?>... parameterTypes) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return getMethodUpInTurn(clazz.getSuperclass(), name, parameterTypes);
        }

    }
}
