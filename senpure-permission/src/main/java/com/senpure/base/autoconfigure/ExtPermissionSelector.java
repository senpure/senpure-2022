package com.senpure.base.autoconfigure;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * ExtPermisisonSelector
 *
 * @author senpure
 * @time 2020-05-20 11:08:05
 */
public class ExtPermissionSelector implements EnvironmentAware, ImportSelector {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> values = new ArrayList<>(16);
        Boolean actuator = environment.getProperty("senpure.permission.actuator",
                Boolean.class, true);
        if (actuator) {
            values.add(ActuatorPermissionAutoConfiguration.class.getName());
        }
        Boolean druid = environment.getProperty("senpure.permission.druid",
                Boolean.class, true);
        if (druid) {
            values.add(DruidPermissionAutoConfiguration.class.getName());
        }
        return values.toArray(new String[0]);
    }
}
