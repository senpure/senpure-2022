package com.senpure.base.generator.hibernate;

import org.hibernate.boot.model.naming.ImplicitNameSource;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * ImplicitNameSourceImpl
 *
 * @author senpure
 * @time 2020-05-14 16:49:46
 */
public class ImplicitNameSourceImpl implements ImplicitNameSource {
    private MetadataBuildingContext buildingContext;

    public ImplicitNameSourceImpl(MetadataBuildingContext buildingContext) {
        this.buildingContext = buildingContext;
    }

    @Override
    public MetadataBuildingContext getBuildingContext() {
        return buildingContext;
    }
}
