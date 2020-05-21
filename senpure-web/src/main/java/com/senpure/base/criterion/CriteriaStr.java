package com.senpure.base.criterion;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 取消掉默认的startData ，endData
 */
public class CriteriaStr implements Serializable {



    private static final long serialVersionUID = 1L;


    @ApiModelProperty( hidden = true,position = 200, value = "页数", example = "2", dataType = "int")
    @Min(value = 1, message = "{input.error}")
    private String page = "1";
    @Min(value = 5, message = "{input.error}")
    @Max(value = 200, message = "{input.error}")
    @ApiModelProperty(hidden = true,position = 201, value = "每页数据", notes = "每页显示多少条数据，默认15条", example = "20",dataType = "int")
    private String pageSize = "15";



    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }


    public String getPageSize() {
        return pageSize;
    }


    public Criteria toCriteria() {
        Criteria criteria = new Criteria();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));

        return criteria;
    }

    protected void beforeStr(StringBuilder sb) {
        sb.append("Criteria{");
    }

    protected void rangeStr(StringBuilder sb) {
        String empty = "";
        sb.append(empty);

    }


    protected void afterStr(StringBuilder sb) {
        sb.append("page=").append(getPage()).append(",");
        sb.append("pageSize=").append(getPageSize()).append(",");
        if (sb.length() > 2) {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("}");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        beforeStr(sb);
        rangeStr(sb);
        afterStr(sb);
        return sb.toString();
    }
}
