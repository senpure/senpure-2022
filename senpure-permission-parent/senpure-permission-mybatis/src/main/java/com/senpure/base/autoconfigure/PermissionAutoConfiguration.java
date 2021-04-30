package com.senpure.base.autoconfigure;

import org.springframework.context.annotation.Import;

/**
 * PermissionAutoConfiguration
 *
 * @author senpure
 * @time 2020-05-20 10:41:23
 */
@Import({
        PermissionComponentAutoConfiguration.class
})
public class PermissionAutoConfiguration {

}
