package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.Permission;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class PermissionCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 534669811L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private String name;
    private String readableName;
    //是否从数据库更新过
    private Boolean databaseUpdate;
    //NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
    private String type;
    //'1,2' type为OWNER 配合verifyName使用
    private String offset;
    //'containerResource',roleResource' type为OWNER 配合offset使用
    private String verifyName;
    private String description;
    //排序
    private Integer sort;

    public static Permission toPermission(PermissionCriteria criteria, Permission permission) {
        permission.setId(criteria.getId());
        permission.setName(criteria.getName());
        permission.setReadableName(criteria.getReadableName());
        permission.setDatabaseUpdate(criteria.getDatabaseUpdate());
        permission.setType(criteria.getType());
        permission.setOffset(criteria.getOffset());
        permission.setVerifyName(criteria.getVerifyName());
        permission.setDescription(criteria.getDescription());
        permission.setSort(criteria.getSort());
        permission.setVersion(criteria.getVersion());
        return permission;
    }

    public Permission toPermission() {
        Permission permission = new Permission();
        return toPermission(this, permission);
    }

    /**
     * 将PermissionCriteria 的有效值(不为空),赋值给 Permission
     *
     * @return Permission
     */
    public Permission effective(Permission permission) {
        if (getId() != null) {
            permission.setId(getId());
        }
        if (getName() != null) {
            permission.setName(getName());
        }
        if (getReadableName() != null) {
            permission.setReadableName(getReadableName());
        }
        if (getDatabaseUpdate() != null) {
            permission.setDatabaseUpdate(getDatabaseUpdate());
        }
        if (getType() != null) {
            permission.setType(getType());
        }
        if (getOffset() != null) {
            permission.setOffset(getOffset());
        }
        if (getVerifyName() != null) {
            permission.setVerifyName(getVerifyName());
        }
        if (getDescription() != null) {
            permission.setDescription(getDescription());
        }
        if (getSort() != null) {
            permission.setSort(getSort());
        }
        if (getVersion() != null) {
            permission.setVersion(getVersion());
        }
        return permission;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("PermissionCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (readableName != null) {
            sb.append("readableName=").append(readableName).append(",");
        }
        if (databaseUpdate != null) {
            sb.append("databaseUpdate=").append(databaseUpdate).append(",");
        }
        if (type != null) {
            sb.append("type=").append(type).append(",");
        }
        if (offset != null) {
            sb.append("offset=").append(offset).append(",");
        }
        if (verifyName != null) {
            sb.append("verifyName=").append(verifyName).append(",");
        }
        if (description != null) {
            sb.append("description=").append(description).append(",");
        }
        if (sort != null) {
            sb.append("sort=").append(sort).append(",");
        }
    }

    /**
     * get (主键)
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public PermissionCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }


    public PermissionCriteria setName(String name) {
        if (name != null && name.trim().length() == 0) {
            this.name = null;
            return this;
        }
        this.name = name;
        return this;
    }

    public String getReadableName() {
        return readableName;
    }


    public PermissionCriteria setReadableName(String readableName) {
        if (readableName != null && readableName.trim().length() == 0) {
            this.readableName = null;
            return this;
        }
        this.readableName = readableName;
        return this;
    }

    /**
     * get 是否从数据库更新过
     *
     * @return
     */
    public Boolean getDatabaseUpdate() {
        return databaseUpdate;
    }

    /**
     * set 是否从数据库更新过
     *
     * @return
     */
    public PermissionCriteria setDatabaseUpdate(Boolean databaseUpdate) {
        this.databaseUpdate = databaseUpdate;
        return this;
    }

    /**
     * get NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
     *
     * @return
     */
    public PermissionCriteria setType(String type) {
        if (type != null && type.trim().length() == 0) {
            this.type = null;
            return this;
        }
        this.type = type;
        return this;
    }

    /**
     * get '1,2' type为OWNER 配合verifyName使用
     *
     * @return
     */
    public String getOffset() {
        return offset;
    }

    /**
     * set '1,2' type为OWNER 配合verifyName使用
     *
     * @return
     */
    public PermissionCriteria setOffset(String offset) {
        if (offset != null && offset.trim().length() == 0) {
            this.offset = null;
            return this;
        }
        this.offset = offset;
        return this;
    }

    /**
     * get 'containerResource',roleResource' type为OWNER 配合offset使用
     *
     * @return
     */
    public String getVerifyName() {
        return verifyName;
    }

    /**
     * set 'containerResource',roleResource' type为OWNER 配合offset使用
     *
     * @return
     */
    public PermissionCriteria setVerifyName(String verifyName) {
        if (verifyName != null && verifyName.trim().length() == 0) {
            this.verifyName = null;
            return this;
        }
        this.verifyName = verifyName;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public PermissionCriteria setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            this.description = null;
            return this;
        }
        this.description = description;
        return this;
    }

    /**
     * get 排序
     *
     * @return
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * set 排序
     *
     * @return
     */
    public PermissionCriteria setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public PermissionCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}