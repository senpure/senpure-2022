package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class SequenceCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 945199211L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 7)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    //标识
    @ApiModelProperty(value = "标识", example = "type", position = 8)
    private String type;
    @ApiModelProperty(example = "prefix", position = 9)
    private String prefix;
    @ApiModelProperty(example = "suffix", position = 10)
    private String suffix;
    @ApiModelProperty(dataType = "int", example = "666666", position = 11)
    private String sequence;
    @ApiModelProperty(dataType = "int", example = "666666", position = 12)
    private String digit;
    @ApiModelProperty(dataType = "int", example = "666666", position = 13)
    private String span;

    public SequenceCriteria toSequenceCriteria() {
        SequenceCriteria criteria = new SequenceCriteria();
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
        //标识
        if (type != null) {
            criteria.setType(type);
        }
        if (prefix != null) {
            criteria.setPrefix(prefix);
        }
        if (suffix != null) {
            criteria.setSuffix(suffix);
        }
        if (sequence != null) {
            criteria.setSequence(Integer.valueOf(sequence));
        }
        if (digit != null) {
            criteria.setDigit(Integer.valueOf(digit));
        }
        if (span != null) {
            criteria.setSpan(Integer.valueOf(span));
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("SequenceCriteriaStr{");
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
    public SequenceCriteriaStr setId(String id) {
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
    public SequenceCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
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
    public SequenceCriteriaStr setType(String type) {
        if (type != null && type.trim().length() == 0) {
            return this;
        }
        this.type = type;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }


    public SequenceCriteriaStr setPrefix(String prefix) {
        if (prefix != null && prefix.trim().length() == 0) {
            return this;
        }
        this.prefix = prefix;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }


    public SequenceCriteriaStr setSuffix(String suffix) {
        if (suffix != null && suffix.trim().length() == 0) {
            return this;
        }
        this.suffix = suffix;
        return this;
    }

    public String getSequence() {
        return sequence;
    }


    public SequenceCriteriaStr setSequence(String sequence) {
        if (sequence != null && sequence.trim().length() == 0) {
            return this;
        }
        this.sequence = sequence;
        return this;
    }

    public String getDigit() {
        return digit;
    }


    public SequenceCriteriaStr setDigit(String digit) {
        if (digit != null && digit.trim().length() == 0) {
            return this;
        }
        this.digit = digit;
        return this;
    }

    public String getSpan() {
        return span;
    }


    public SequenceCriteriaStr setSpan(String span) {
        if (span != null && span.trim().length() == 0) {
            return this;
        }
        this.span = span;
        return this;
    }

}