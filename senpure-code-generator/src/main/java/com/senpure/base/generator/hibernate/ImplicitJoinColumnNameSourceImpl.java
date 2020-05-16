package com.senpure.base.generator.hibernate;

import com.senpure.base.generator.Model;
import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * ImplicitJoinColumnNameSourceImpl
 * 只处理 property为entity的情况
 *
 * @author senpure
 * @time 2020-05-14 16:35:47
 */
public class ImplicitJoinColumnNameSourceImpl implements ImplicitJoinColumnNameSource {
    private MetadataBuildingContext buildingContext;
    private EntityNaming entityNaming;
    private AttributePath attributePath;
    private Model referenceModel;


    public ImplicitJoinColumnNameSourceImpl(String propertyName, Model referencedModel, MetadataBuildingContext buildingContext) {
        this.buildingContext = buildingContext;
        this.entityNaming = new EntityNamingImpl(referencedModel.getClazz());

        this.referenceModel = referencedModel;
        attributePath = AttributePath.parse(propertyName);
    }

    @Override
    public Nature getNature() {
        return Nature.ENTITY;
    }

    @Override
    public EntityNaming getEntityNaming() {
        return entityNaming;
    }

    @Override
    public AttributePath getAttributePath() {
        return attributePath;
    }

    @Override
    public Identifier getReferencedTableName() {
        return Identifier.toIdentifier(referenceModel.getTableName());
    }

    @Override
    public Identifier getReferencedColumnName() {
        return Identifier.toIdentifier(referenceModel.getId().getColumn());
    }

    @Override
    public MetadataBuildingContext getBuildingContext() {
        return buildingContext;
    }
}
