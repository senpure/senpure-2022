package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.Role;
import com.senpure.base.util.DateFormatUtil;

import java.util.Date;
import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class RoleCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 1021021432L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private String name;
    private Date createDate;
    private Date startCreateDate;
    private Date endCreateDate;
    //table [senpure_role][column = create_date] criteriaOrder
    private String createDateOrder;
    private String description;
    //(外键,modelName:Container,tableName:senpure_container)
    private Integer containerId;
    //table [senpure_role][column = container_id] criteriaOrder
    private String containerIdOrder;

    public static Role toRole(RoleCriteria criteria, Role role) {
        role.setId(criteria.getId());
        role.setName(criteria.getName());
        role.setCreateDate(criteria.getCreateDate());
        if (criteria.getCreateDate() != null) {
            role.setCreateTime(criteria.getCreateDate().getTime());
        }
        role.setDescription(criteria.getDescription());
        role.setContainerId(criteria.getContainerId());
        role.setVersion(criteria.getVersion());
        return role;
    }

    public Role toRole() {
        Role role = new Role();
        return toRole(this, role);
    }

    /**
     * 将RoleCriteria 的有效值(不为空),赋值给 Role
     *
     * @return Role
     */
    public Role effective(Role role) {
        if (getId() != null) {
            role.setId(getId());
        }
        if (getName() != null) {
            role.setName(getName());
        }
        if (getCreateDate() != null) {
            role.setCreateDate(getCreateDate());
        }
        if (getDescription() != null) {
            role.setDescription(getDescription());
        }
        if (getContainerId() != null) {
            role.setContainerId(getContainerId());
        }
        if (getVersion() != null) {
            role.setVersion(getVersion());
        }
        return role;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startCreateDate != null) {
            sb.append("startCreateDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startCreateDate)).append(",");
        }
        if (endCreateDate != null) {
            sb.append("endCreateDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endCreateDate)).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RoleCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (createDate != null) {
            sb.append("createDate=").append(createDate).append(",");
        }
        if (description != null) {
            sb.append("description=").append(description).append(",");
        }
        if (containerId != null) {
            sb.append("containerId=").append(containerId).append(",");
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
    public RoleCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }


    public RoleCriteria setName(String name) {
        if (name != null && name.trim().length() == 0) {
            this.name = null;
            return this;
        }
        this.name = name;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public RoleCriteria setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public RoleCriteria setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
        return this;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public RoleCriteria setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
        return this;
    }

    /**
     * get table [senpure_role][column = create_date] criteriaOrder
     *
     * @return
     */
    public String getCreateDateOrder() {
        return createDateOrder;
    }

    /**
     * set table [senpure_role][column = create_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleCriteria setCreateDateOrder(String createDateOrder) {
        this.createDateOrder = createDateOrder;
        putSort("create_date", createDateOrder);
        return this;
    }

    public String getDescription() {
        return description;
    }


    public RoleCriteria setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            this.description = null;
            return this;
        }
        this.description = description;
        return this;
    }

    /**
     * get (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public Integer getContainerId() {
        return containerId;
    }

    /**
     * set (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public RoleCriteria setContainerId(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    /**
     * get table [senpure_role][column = container_id] criteriaOrder
     *
     * @return
     */
    public String getContainerIdOrder() {
        return containerIdOrder;
    }

    /**
     * set table [senpure_role][column = container_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleCriteria setContainerIdOrder(String containerIdOrder) {
        this.containerIdOrder = containerIdOrder;
        putSort("container_id", containerIdOrder);
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
    public RoleCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}