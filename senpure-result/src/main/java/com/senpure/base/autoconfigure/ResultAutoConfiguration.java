package com.senpure.base.autoconfigure;

import com.senpure.base.Result;
import com.senpure.base.ResultHelper;
import org.springframework.context.annotation.Bean;

/**
 * ResultAutoConfiguration
 *
 * @author senpure
 * @time 2019-01-18 17:39:58
 */
public class ResultAutoConfiguration {

    @Bean
    public ResultHelper resultHelper() {
        return new ResultHelper();
    }

    @Bean
    public Result result() {
        return new Result();
    }
}
