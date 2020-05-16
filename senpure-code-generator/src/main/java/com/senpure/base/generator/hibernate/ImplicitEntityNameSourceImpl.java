package com.senpure.base.generator.hibernate;

import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * ImplicitEntityNameSourceImpl
 *
 * @author senpure
 * @time 2020-05-14 14:11:32
 */
public class ImplicitEntityNameSourceImpl implements ImplicitEntityNameSource {
    private EntityNaming entityNaming;
    private MetadataBuildingContext buildingContext;

    public ImplicitEntityNameSourceImpl(Class<?> clazz,MetadataBuildingContext buildingContext) {
        entityNaming = new EntityNamingImpl(clazz);
        this.buildingContext=buildingContext;
    }

    @Override
    public EntityNaming getEntityNaming() {
        return entityNaming;
    }

    @Override
    public MetadataBuildingContext getBuildingContext() {
        return buildingContext;
    }
}
