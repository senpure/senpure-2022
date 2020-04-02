package com.senpure.dispatcher.entity;

import com.senpure.base.entity.VersionEntity;

import javax.persistence.*;

/**
 * RangeConfig
 *
 * @author senpure
 * @time 2019-08-01 15:54:50
 */
@Entity
public class RangeConfig extends VersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long start;
    @Column(nullable = false)
    private Long end;
    @Column(nullable = false)
    private Long step;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }
}
