package com.senpure.dispatcher.entity;

import com.senpure.base.entity.VersionEntity;

import javax.persistence.*;

/**
 * RangeValue
 *
 * @author senpure
 * @time 2019-08-01 15:58:42
 */
@Entity
public class RangeValue extends VersionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer configId;
    @Column(nullable = false)
    private  String configName;
    @Column(nullable = false)
    private String serverName;
    @Column(nullable = false)
    private String serverKey;
    @Column(nullable = false)
    private Long start;
    @Column(nullable = false)
    private Long end;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
