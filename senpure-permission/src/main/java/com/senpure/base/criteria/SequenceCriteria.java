package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.Sequence;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class SequenceCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 945199211L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    //标识
    private String type;
    private String prefix;
    private String suffix;
    private Integer sequence;
    private Integer digit;
    private Integer span;

    public static Sequence toSequence(SequenceCriteria criteria, Sequence sequence) {
        sequence.setId(criteria.getId());
        sequence.setType(criteria.getType());
        sequence.setPrefix(criteria.getPrefix());
        sequence.setSuffix(criteria.getSuffix());
        sequence.setSequence(criteria.getSequence());
        sequence.setDigit(criteria.getDigit());
        sequence.setSpan(criteria.getSpan());
        sequence.setVersion(criteria.getVersion());
        return sequence;
    }

    public Sequence toSequence() {
        Sequence sequence = new Sequence();
        return toSequence(this, sequence);
    }

    /**
     * 将SequenceCriteria 的有效值(不为空),赋值给 Sequence
     *
     * @return Sequence
     */
    public Sequence effective(Sequence sequence) {
        if (getId() != null) {
            sequence.setId(getId());
        }
        if (getType() != null) {
            sequence.setType(getType());
        }
        if (getPrefix() != null) {
            sequence.setPrefix(getPrefix());
        }
        if (getSuffix() != null) {
            sequence.setSuffix(getSuffix());
        }
        if (getSequence() != null) {
            sequence.setSequence(getSequence());
        }
        if (getDigit() != null) {
            sequence.setDigit(getDigit());
        }
        if (getSpan() != null) {
            sequence.setSpan(getSpan());
        }
        if (getVersion() != null) {
            sequence.setVersion(getVersion());
        }
        return sequence;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("SequenceCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (type != null) {
            sb.append("type=").append(type).append(",");
        }
        if (prefix != null) {
            sb.append("prefix=").append(prefix).append(",");
        }
        if (suffix != null) {
            sb.append("suffix=").append(suffix).append(",");
        }
        if (sequence != null) {
            sb.append("sequence=").append(sequence).append(",");
        }
        if (digit != null) {
            sb.append("digit=").append(digit).append(",");
        }
        if (span != null) {
            sb.append("span=").append(span).append(",");
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
    public SequenceCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * get 标识
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set 标识
     *
     * @return
     */
    public SequenceCriteria setType(String type) {
        if (type != null && type.trim().length() == 0) {
            this.type = null;
            return this;
        }
        this.type = type;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }


    public SequenceCriteria setPrefix(String prefix) {
        if (prefix != null && prefix.trim().length() == 0) {
            this.prefix = null;
            return this;
        }
        this.prefix = prefix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }


    public SequenceCriteria setSuffix(String suffix) {
        if (suffix != null && suffix.trim().length() == 0) {
            this.suffix = null;
            return this;
        }
        this.suffix = suffix;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }


    public SequenceCriteria setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Integer getDigit() {
        return digit;
    }


    public SequenceCriteria setDigit(Integer digit) {
        this.digit = digit;
        return this;
    }

    public Integer getSpan() {
        return span;
    }


    public SequenceCriteria setSpan(Integer span) {
        this.span = span;
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
    public SequenceCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}