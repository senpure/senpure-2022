package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.PermissionMenu;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class PermissionMenuCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 343182696L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private Integer menuId;
    private String permissionName;
    private Boolean dataBaseUpdate;

    public static PermissionMenu toPermissionMenu(PermissionMenuCriteria criteria, PermissionMenu permissionMenu) {
        permissionMenu.setId(criteria.getId());
        permissionMenu.setMenuId(criteria.getMenuId());
        permissionMenu.setPermissionName(criteria.getPermissionName());
        permissionMenu.setDataBaseUpdate(criteria.getDataBaseUpdate());
        permissionMenu.setVersion(criteria.getVersion());
        return permissionMenu;
    }

    public PermissionMenu toPermissionMenu() {
        PermissionMenu permissionMenu = new PermissionMenu();
        return toPermissionMenu(this, permissionMenu);
    }

    /**
     * 将PermissionMenuCriteria 的有效值(不为空),赋值给 PermissionMenu
     *
     * @return PermissionMenu
     */
    public PermissionMenu effective(PermissionMenu permissionMenu) {
        if (getId() != null) {
            permissionMenu.setId(getId());
        }
        if (getMenuId() != null) {
            permissionMenu.setMenuId(getMenuId());
        }
        if (getPermissionName() != null) {
            permissionMenu.setPermissionName(getPermissionName());
        }
        if (getDataBaseUpdate() != null) {
            permissionMenu.setDataBaseUpdate(getDataBaseUpdate());
        }
        if (getVersion() != null) {
            permissionMenu.setVersion(getVersion());
        }
        return permissionMenu;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("PermissionMenuCriteria{");
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
    public PermissionMenuCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getMenuId() {
        return menuId;
    }


    public PermissionMenuCriteria setMenuId(Integer menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getPermissionName() {
        return permissionName;
    }


    public PermissionMenuCriteria setPermissionName(String permissionName) {
        if (permissionName != null && permissionName.trim().length() == 0) {
            this.permissionName = null;
            return this;
        }
        this.permissionName = permissionName;
        return this;
    }

    public Boolean getDataBaseUpdate() {
        return dataBaseUpdate;
    }


    public PermissionMenuCriteria setDataBaseUpdate(Boolean dataBaseUpdate) {
        this.dataBaseUpdate = dataBaseUpdate;
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
    public PermissionMenuCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}