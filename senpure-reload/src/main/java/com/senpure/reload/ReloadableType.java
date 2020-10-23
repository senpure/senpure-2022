package com.senpure.reload;

import com.senpure.reload.agent.SenpureReloadAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * ReloadableType
 *
 * @author senpure
 * @time 2020-10-16 16:41:56
 */
public class ReloadableType {

    private WeakReference<RedefineClassLoader> redefineClassLoader;
    private ClassLoader classLoader;
    private String className;
    private Method defineClassMethod = null;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    private Class<?> clazz;

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void redefineClass(byte[] newBytes) {

        redefineClass(newBytes, false);
    }

    public void redefineClass(byte[] newBytes, boolean permanent) {
        RedefineClassLoader loader = redefineClassLoader == null ? null : redefineClassLoader.get();
        if (loader == null) {
            loader = new RedefineClassLoader(classLoader);
            redefineClassLoader = new WeakReference<>(loader);
        }
        try {
            if (permanent) {
                if (defineClassMethod == null) {
                    defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass",
                            String.class, byte[].class, int.class, int.class);
                }
                defineClassMethod.setAccessible(true);
                ClassLoader loaderToUse;
                loaderToUse = redefineClassLoader.get();
                clazz = (Class<?>) defineClassMethod.invoke(loaderToUse,
                        new Object[]{className, newBytes, 0, newBytes.length});
            } else {

                Instrumentation instrumentation = SenpureReloadAgent.getInstrumentation();
                for (Class<?> loadedClass : instrumentation.getAllLoadedClasses()) {
                    if (loadedClass.getName().equals(className)) {

//                        ClassFileLocator classFileLocator = ClassFileLocator.Simple.of(className, newBytes);
//                        new ByteBuddy().rebase(loadedClass, classFileLocator)
//                                .make().load(classLoader, ClassReloadingStrategy.of(instrumentation))
//                        ;
                        instrumentation.redefineClasses(new ClassDefinition(loadedClass, newBytes));
                        break;
                    }
                }
              //  instrumentation.redefineClasses(new ClassDefinition(  Class.forName(className,false,classLoader), newBytes));


               // clazz = loader.defineClass(className, newBytes);
                logger.debug("clazz --------------------------");

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private byte[] retransform(byte[] bytes) {
        return bytes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
