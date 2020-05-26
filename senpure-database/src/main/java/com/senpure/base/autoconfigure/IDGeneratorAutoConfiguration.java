package com.senpure.base.autoconfigure;


import com.senpure.base.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@ConditionalOnProperty(
        prefix = "senpure.snowflake",
        name = {"enabled"}
        , matchIfMissing = true
)
@EnableConfigurationProperties(IDGeneratorAutoConfiguration.SnowflakeProperties.class)
public class IDGeneratorAutoConfiguration {
    protected Logger logger = LoggerFactory.getLogger(IDGeneratorAutoConfiguration.class);
    @Resource
    private SnowflakeProperties snowflakeProperties;

    @Bean
    public IDGenerator getIdGenerator() {
        logger.debug("workerId is {} dataCenterId is {}", snowflakeProperties.getWorkerId(), snowflakeProperties.getDataCenterId());
        return new IDGenerator(snowflakeProperties.getDataCenterId(), snowflakeProperties.getWorkerId());
    }

    @ConfigurationProperties(
            prefix = "senpure.snowflake")
    public static class SnowflakeProperties {
        /**
         * 数据中心id
         */
        private int dataCenterId = 0;
        /**
         * 工作机器id
         */
        private int workerId = 0;

        public int getDataCenterId() {
            return dataCenterId;
        }

        public SnowflakeProperties setDataCenterId(int dataCenterId) {
            this.dataCenterId = dataCenterId;
            return this;
        }

        public int getWorkerId() {
            return workerId;
        }

        public SnowflakeProperties setWorkerId(int workerId) {
            this.workerId = workerId;
            return this;
        }
    }


}
