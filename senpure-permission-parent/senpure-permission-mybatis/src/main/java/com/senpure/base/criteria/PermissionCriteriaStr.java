package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 17:23:15
 */
public class PermissionCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 2096486843L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 10)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "name", position = 11)
    private String name;
    @ApiModelProperty(example = "readableName", position = 12)
    private String readableName;
    //是否从数据库更新过
    @ApiModelProperty(value = "是否从数据库更新过", dataType = "boolean", position = 13)
    private String databaseUpdate;
    //服务名(多个服务可能共用一个数据库来存放权限)
    @ApiModelProperty(value = "服务名(多个服务可能共用一个数据库来存放权限)", example = "serverName", position = 14)
    private String serverName;
    //NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
    @ApiModelProperty(value = "NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)", example = "type", position = 15)
    private String type;
    //'1,2' type为OWNER 配合verifyName使用
    @ApiModelProperty(value = "'1,2' type为OWNER 配合verifyName使用", example = "offset", position = 16)
    private String offset;
    //'containerResource',roleResource' type为OWNER 配合offset使用
    @ApiModelProperty(value = "'containerResource',roleResource' type为OWNER 配合offset使用", example = "verifyName", position = 17)
    private String verifyName;
    @ApiModelProperty(example = "description", position = 18)
    private String description;
    //排序
    @ApiModelProperty(value = "排序", dataType = "int", example = "666666", position = 19)
    private String sort;
    //table [senpure_permission][column = name] criteriaOrder
    @ApiModelProperty(value = "name 排序" , allowableValues = "ASC,DESC", position = 20)
    private String nameOrder;
    //table [senpure_permission][column = server_name] criteriaOrder
    @ApiModelProperty(value = "serverName 排序" , allowableValues = "ASC,DESC", position = 21)
    private String serverNameOrder;
    //table [senpure_permission][column = type] criteriaOrder
    @ApiModelProperty(value = "type 排序" , allowableValues = "ASC,DESC", position = 22)
    private String typeOrder;
    //table [senpure_permission][column = verify_name] criteriaOrder
    @ApiModelProperty(value = "verifyName 排序" , allowableValues = "ASC,DESC", position = 23)
    private String verifyNameOrder;

    public PermissionCriteria toPermissionCriteria() {
        PermissionCriteria criteria = new PermissionCriteria();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Long.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        if (name != null) {
            criteria.setName(name);
        }
        //table [senpure_permission][column = name] criteriaOrder
        if (nameOrder != null) {
            criteria.setNameOrder(nameOrder);
        }
        if (readableName != null) {
            criteria.setReadableName(readableName);
        }
        //是否从数据库更新过
        if (databaseUpdate != null) {
            criteria.setDatabaseUpdate(Boolean.valueOf(databaseUpdate));
        }
        //服务名(多个服务可能共用一个数据库来存放权限)
        if (serverName != null) {
            criteria.setServerName(serverName);
        }
        //table [senpure_permission][column = server_name] criteriaOrder
        if (serverNameOrder != null) {
            criteria.setServerNameOrder(serverNameOrder);
        }
        //NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
        if (type != null) {
            criteria.setType(type);
        }
        //table [senpure_permission][column = type] criteriaOrder
        if (typeOrder != null) {
            criteria.setTypeOrder(typeOrder);
        }
        //'1,2' type为OWNER 配合verifyName使用
        if (offset != null) {
            criteria.setOffset(offset);
        }
        //'containerResource',roleResource' type为OWNER 配合offset使用
        if (verifyName != null) {
            criteria.setVerifyName(verifyName);
        }
        //table [senpure_permission][column = verify_name] criteriaOrder
        if (verifyNameOrder != null) {
            criteria.setVerifyNameOrder(verifyNameOrder);
        }
        if (description != null) {
            criteria.setDescription(description);
        }
        //排序
        if (sort != null) {
            criteria.setSort(Integer.valueOf(sort));
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("PermissionCriteriaStr{");
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
        if (serverName != null) {
            sb.append("serverName=").append(serverName).append(",");
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

    @Override
    protected void afterStr(StringBuilder sb) {
        if (nameOrder != null) {
            sb.append("nameOrder=").append(nameOrder).append(",");
        }
        if (serverNameOrder != null) {
            sb.append("serverNameOrder=").append(serverNameOrder).append(",");
        }
        if (typeOrder != null) {
            sb.append("typeOrder=").append(typeOrder).append(",");
        }
        if (verifyNameOrder != null) {
            sb.append("verifyNameOrder=").append(verifyNameOrder).append(",");
        }
        super.afterStr(sb);
    }

    /**
     * get (主键)
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public PermissionCriteriaStr setId(String id) {
        if (id != null && id.trim().length() == 0) {
            return this;
        }
        this.id = id;
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public PermissionCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }


    public PermissionCriteriaStr setName(String name) {
        if (name != null && name.trim().length() == 0) {
            return this;
        }
        this.name = name;
        return this;
    }

    /**
     * get table [senpure_permission][column = name] criteriaOrder
     *
     * @return
     */
    public String getNameOrder() {
        return nameOrder;
    }

    /**
     * set table [senpure_permission][column = name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public PermissionCriteriaStr setNameOrder(String nameOrder) {
        if (nameOrder != null && nameOrder.trim().length() == 0) {
            this.nameOrder = null;
            return this;
        }
        this.nameOrder = nameOrder;
        return this;
    }

    public String getReadableName() {
        return readableName;
    }


    public PermissionCriteriaStr setReadableName(String readableName) {
        if (readableName != null && readableName.trim().length() == 0) {
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
    public String getDatabaseUpdate() {
        return databaseUpdate;
    }

    /**
     * set 是否从数据库更新过
     *
     * @return
     */
    public PermissionCriteriaStr setDatabaseUpdate(String databaseUpdate) {
        if (databaseUpdate != null && databaseUpdate.trim().length() == 0) {
            return this;
        }
        this.databaseUpdate = databaseUpdate;
        return this;
    }

    /**
     * get 服务名(多个服务可能共用一个数据库来存放权限)
     *
     * @return
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * set 服务名(多个服务可能共用一个数据库来存放权限)
     *
     * @return
     */
    public PermissionCriteriaStr setServerName(String serverName) {
        if (serverName != null && serverName.trim().length() == 0) {
            return this;
        }
        this.serverName = serverName;
        return this;
    }

    /**
     * get table [senpure_permission][column = server_name] criteriaOrder
     *
     * @return
     */
    public String getServerNameOrder() {
        return serverNameOrder;
    }

    /**
     * set table [senpure_permission][column = server_name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public PermissionCriteriaStr setServerNameOrder(String serverNameOrder) {
        if (serverNameOrder != null && serverNameOrder.trim().length() == 0) {
            this.serverNameOrder = null;
            return this;
        }
        this.serverNameOrder = serverNameOrder;
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
    public PermissionCriteriaStr setType(String type) {
        if (type != null && type.trim().length() == 0) {
            return this;
        }
        this.type = type;
        return this;
    }

    /**
     * get table [senpure_permission][column = type] criteriaOrder
     *
     * @return
     */
    public String getTypeOrder() {
        return typeOrder;
    }

    /**
     * set table [senpure_permission][column = type] criteriaOrder DESC||ASC
     *
     * @return
     */
    public PermissionCriteriaStr setTypeOrder(String typeOrder) {
        if (typeOrder != null && typeOrder.trim().length() == 0) {
            this.typeOrder = null;
            return this;
        }
        this.typeOrder = typeOrder;
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
    public PermissionCriteriaStr setOffset(String offset) {
        if (offset != null && offset.trim().length() == 0) {
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
    public PermissionCriteriaStr setVerifyName(String verifyName) {
        if (verifyName != null && verifyName.trim().length() == 0) {
            return this;
        }
        this.verifyName = verifyName;
        return this;
    }

    /**
     * get table [senpure_permission][column = verify_name] criteriaOrder
     *
     * @return
     */
    public String getVerifyNameOrder() {
        return verifyNameOrder;
    }

    /**
     * set table [senpure_permission][column = verify_name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public PermissionCriteriaStr setVerifyNameOrder(String verifyNameOrder) {
        if (verifyNameOrder != null && verifyNameOrder.trim().length() == 0) {
            this.verifyNameOrder = null;
            return this;
        }
        this.verifyNameOrder = verifyNameOrder;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public PermissionCriteriaStr setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
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
    public String getSort() {
        return sort;
    }

    /**
     * set 排序
     *
     * @return
     */
    public PermissionCriteriaStr setSort(String sort) {
        if (sort != null && sort.trim().length() == 0) {
            return this;
        }
        this.sort = sort;
        return this;
    }

}