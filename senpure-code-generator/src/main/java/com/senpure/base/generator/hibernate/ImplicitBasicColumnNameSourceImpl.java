package com.senpure.base.generator.hibernate;

import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * ImplicitBasicColumnNameSourceImpl
 *
 * @author senpure
 * @time 2020-05-15 14:59:02
 */
public class ImplicitBasicColumnNameSourceImpl implements ImplicitBasicColumnNameSource {
    private AttributePath attributePath;
    private MetadataBuildingContext buildingContext;

    public ImplicitBasicColumnNameSourceImpl(String propertyName, MetadataBuildingContext buildingContext) {
        attributePath = AttributePath.parse(propertyName);
        this.buildingContext = buildingContext;
    }

    @Override
    public AttributePath getAttributePath() {
        return attributePath;
    }

    @Override
    public boolean isCollectionElement() {
        return false;
    }

    @Override
    public MetadataBuildingContext getBuildingContext() {
        return buildingContext;
    }
}
