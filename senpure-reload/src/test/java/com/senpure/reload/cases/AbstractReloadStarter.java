package com.senpure.reload.cases;

import com.senpure.base.AppEvn;
import com.senpure.base.util.Spring;
import com.senpure.reload.agent.SenpureReloadAgent;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springsource.loaded.agent.SpringLoadedAgent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ReloadStarter
 *
 * @author senpure
 * @time 2020-10-19 15:44:23
 */
public abstract class AbstractReloadStarter {
    private final AtomicInteger amount = new AtomicInteger();

    private static boolean useSenpure = false;

    private static boolean useExecutor = false;
    private static boolean useSpring = false;

    private static boolean useNewObj = true;

    public static void start(boolean useSenpure, boolean useExecutor, boolean useSpring) {
        AbstractReloadStarter.useSenpure = useSenpure;
        AbstractReloadStarter.useExecutor = useExecutor;
        AbstractReloadStarter.useSpring = useSpring;
        start();
    }

    public static void start() {

        if (useSenpure) {
            SenpureReloadAgent.agentmain(null, ByteBuddyAgent.install());
        } else {
            AppEvn.markClassRootPath(AbstractReloadStarter.class);
            StringBuilder args = new StringBuilder();
            args.append("verbose=true;");
            args.append("logging=true;");

            args.append("dumpFolder=").append(AppEvn.getClassRootPath()).append("2;");
            args.append("dump=com.senpure.reload.cases.normal.NormalMethod");
            args.append(",");
            args.append("com.senpure.reload.cases.lambda.LambdaStatic");
            args.append(";");
            System.setProperty("springloaded", args.toString());
            SpringLoadedAgent.agentmain(null, ByteBuddyAgent.install());

        }

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[stackTraceElements.length - 1].getClassName();
        System.out.println(name);
        try {
            Class<?> clazz = Class.forName(name);
            Object obj;
            if (useSpring) {

                ConfigurableApplicationContext applicationContext =
                        SpringApplication.run(clazz, "5");
                Spring.regApplicationContext(applicationContext);

                obj = Spring.getBean(clazz);

            } else {
                obj = clazz.newInstance();
            }


            AbstractReloadStarter starter = (AbstractReloadStarter) obj;

            Class<?> testCaseClazz = Class.forName(name.replace("ReloadStarter", ""));

            if (useSpring) {

                try {
                    obj = Spring.getBean(testCaseClazz);
                } catch (BeansException e) {
                    Spring.regBean(testCaseClazz);
                    obj = Spring.getBean(testCaseClazz);
                }

            } else {
                obj = testCaseClazz.newInstance();
            }
            ReloadCase reloadCase = (ReloadCase) obj;
            starter.execute(reloadCase);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


    }

    private ReloadCase getReloadCase(ReloadCase reloadCase) {
        if (useNewObj) {
            if (useSpring) {
                return Spring.getBean(reloadCase.getClass());
            }
            try {
                return reloadCase.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return reloadCase;
        }
        return reloadCase;
    }

    public void execute(ReloadCase reloadCase) {

        if (useExecutor) {
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                try {
                    ReloadCase useCase = getReloadCase(reloadCase);
                    useCase.execute();
                    System.out.println("--------------" + amount.incrementAndGet());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }, 1000, 2000, TimeUnit.MILLISECONDS);
        } else {
            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        ReloadCase useCase = getReloadCase(reloadCase);
                        useCase.execute();
                        System.out.println("--------------" + amount.incrementAndGet());
                        Thread.sleep(2000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.setDaemon(false);
            thread.start();

        }

    }

}
