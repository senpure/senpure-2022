package com.senpure.database.id;

import com.senpure.base.util.IDGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class SnowflakeGenerator implements IdentifierGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowflakeGenerator.class);
    private static IDGenerator idGenerator = new IDGenerator(0, 0);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return idGenerator.nextId();
    }

    public static void setIdGenerator(IDGenerator idGenerator) {
        SnowflakeGenerator.idGenerator = idGenerator;
        LOGGER.debug("设置雪花生成器 {}", idGenerator);
    }
}
