package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class PermissionMenuCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 343182696L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 4)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(dataType = "int", example = "666666", position = 5)
    private String menuId;
    @ApiModelProperty(example = "permissionName", position = 6)
    private String permissionName;
    @ApiModelProperty(dataType = "boolean", position = 7)
    private String dataBaseUpdate;

    public PermissionMenuCriteria toPermissionMenuCriteria() {
        PermissionMenuCriteria criteria = new PermissionMenuCriteria();
        criteria.setPage(Integer.valueOf(getPage()));
        criteria.setPageSize(Integer.valueOf(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Long.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        if (menuId != null) {
            criteria.setMenuId(Integer.valueOf(menuId));
        }
        if (permissionName != null) {
            criteria.setPermissionName(permissionName);
        }
        if (dataBaseUpdate != null) {
            criteria.setDataBaseUpdate(Boolean.valueOf(dataBaseUpdate));
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("PermissionMenuCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (menuId != null) {
            sb.append("menuId=").append(menuId).append(",");
        }
        if (permissionName != null) {
            sb.append("permissionName=").append(permissionName).append(",");
        }
        if (dataBaseUpdate != null) {
            sb.append("dataBaseUpdate=").append(dataBaseUpdate).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
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
    public PermissionMenuCriteriaStr setId(String id) {
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
    public PermissionMenuCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getMenuId() {
        return menuId;
    }


    public PermissionMenuCriteriaStr setMenuId(String menuId) {
        if (menuId != null && menuId.trim().length() == 0) {
            return this;
        }
        this.menuId = menuId;
        return this;
    }

    public String getPermissionName() {
        return permissionName;
    }


    public PermissionMenuCriteriaStr setPermissionName(String permissionName) {
        if (permissionName != null && permissionName.trim().length() == 0) {
            return this;
        }
        this.permissionName = permissionName;
        return this;
    }

    public String getDataBaseUpdate() {
        return dataBaseUpdate;
    }


    public PermissionMenuCriteriaStr setDataBaseUpdate(String dataBaseUpdate) {
        if (dataBaseUpdate != null && dataBaseUpdate.trim().length() == 0) {
            return this;
        }
        this.dataBaseUpdate = dataBaseUpdate;
        return this;
    }

}