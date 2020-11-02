package com.senpure.reload.cases.normal;

import com.senpure.base.AppEvn;
import com.senpure.reload.cases.AbstractReloadTestSupport;
import com.senpure.reload.cases.ReloadCase;
import com.senpure.reload.cases.lambda.Add;
import net.bytebuddy.ByteBuddy;
import org.springsource.loaded.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Normal
 *
 * @author senpure
 * @time 2020-10-19 17:04:48
 */
public class NormalMethod extends AbstractReloadTestSupport {
    int c = 0;

    int d = 0;

    @Override
    public void execute() {
        TypeRegistry typeRegistry = TypeRegistry.getTypeRegistryFor(getClass().getClassLoader());
        ReloadableType reloadableType = typeRegistry.getReloadableType(0);
        String folder = "";
        if (c == 0) {
            //   AppEvn.markClassRootPath(NormalMethod.class);


            // Utils.dump()

            folder = GlobalConfiguration.dumpFolder;
            if (folder == null) {
                folder = AppEvn.getClassRootPath();
            }
            folder += "/" + getClass().getPackage().getName() + "/";
            File file = new File(folder);
            file.mkdir();
            Utils.dumpClass(folder + "NormalMethod.class", typeRegistry.getReloadableType(0).bytesLoaded);
            Utils.dumpClass(folder + "NormalMethod__I.class", typeRegistry.getReloadableType(0).interfaceBytes);
            c++;
        }
        if (reloadableType.hasBeenReloaded()
        ) {
            if (d == 0) {

                d++;
                // Utils.dumpClass(folder + "NormalMethod$$.class", typeRegistry.getReloadableType(0).getLiveVersion().);
            }
        }

        try {

            CurrentLiveVersion currentLiveVersion = typeRegistry.getReloadableType(0).getLiveVersion();

            if (currentLiveVersion != null) {

                System.out.println("not null");
                d++;


                Class<?> ci = Class.forName(getClass().getName() + "__I");
                Class<?> cb = Class.forName(getClass().getName() + "$$E" + currentLiveVersion.getVersionStamp());
                System.out.println(ci);
                System.out.println(cb);
                System.out.println(ci.isAssignableFrom(cb));

                System.out.println(ci.isInstance(reloadableType.fetchLatest()));

            }


            System.out.println(ReloadCase.class.isAssignableFrom(getClass()));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("normal method 12");
        say();
    }

    public void say() {

        ByteBuddy byteBuddy = new ByteBuddy();

        System.out.println("say method 3");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            list.add(i);
            list.add(i);

        }
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer integer : list) {
            map.merge(integer, 1, Add::sum);
        }

    }

    public void say4() {

        ByteBuddy byteBuddy = new ByteBuddy();

        System.out.println("say method 4");

    }

    public static void main(String[] args) {
        start();
    }
}
