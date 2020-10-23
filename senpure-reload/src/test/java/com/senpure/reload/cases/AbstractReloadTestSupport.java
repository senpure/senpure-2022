package com.senpure.reload.cases;

import com.senpure.base.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ReloadTestSupport
 *
 * @author senpure
 * @time 2020-10-16 15:58:04
 */
public abstract class AbstractReloadTestSupport implements ReloadCase {


    public static void start() {


        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[stackTraceElements.length - 1].getClassName();
        try {
            Class<?> clazz = Class.forName(name);
            Object obj = clazz.newInstance();
            Method method = ClassUtil.getMethodUpInTurn(clazz, "run");
            if (method != null) {
                method.invoke(obj);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private final AtomicInteger amount = new AtomicInteger();
    public  void run() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                execute();
                System.out.println("--------------" + amount.incrementAndGet());
            }
        }, 1000,1000, TimeUnit.MILLISECONDS);
    }

    public abstract void execute();
}
