package com.senpure.base.generator.hibernate;

import org.hibernate.boot.model.naming.EntityNaming;

import javax.persistence.Entity;

/**
 * EntityNamingImpl
 *
 * @author senpure
 * @time 2020-05-14 14:12:10
 */
public class EntityNamingImpl implements EntityNaming {
    private final Class<?> clazz;

    public EntityNamingImpl(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getClassName() {
        return clazz.getName();
    }

    @Override
    public String getEntityName() {
        return clazz.getName();
    }

    @Override
    public String getJpaEntityName() {
//        Table table = clazz.getAnnotation(Table.class);
//        if (table != null) {
//            return table.name();
//        }
        Entity entity = clazz.getAnnotation(Entity.class);
        if (entity != null) {
            return entity.name();
        }
        return null;
    }
}
