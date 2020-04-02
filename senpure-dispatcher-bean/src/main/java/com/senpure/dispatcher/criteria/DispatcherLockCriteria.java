package com.senpure.dispatcher.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.dispatcher.model.DispatcherLock;

import java.io.Serializable;

/**
 * SnowflakeLock
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class DispatcherLockCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 0L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;

    public static DispatcherLock toDispatcherLock(DispatcherLockCriteria criteria, DispatcherLock dispatcherLock) {
        dispatcherLock.setId(criteria.getId());
        dispatcherLock.setVersion(criteria.getVersion());
        return dispatcherLock;
    }

    public DispatcherLock toDispatcherLock() {
        DispatcherLock dispatcherLock = new DispatcherLock();
        return toDispatcherLock(this, dispatcherLock);
    }

    /**
     * 将DispatcherLockCriteria 的有效值(不为空),赋值给 DispatcherLock
     *
     * @return DispatcherLock
     */
    public DispatcherLock effective(DispatcherLock dispatcherLock) {
        if (getId() != null) {
            dispatcherLock.setId(getId());
        }
        if (getVersion() != null) {
            dispatcherLock.setVersion(getVersion());
        }
        return dispatcherLock;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("DispatcherLockCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
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
    public DispatcherLockCriteria setId(Long id) {
        this.id = id;
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
    public DispatcherLockCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}