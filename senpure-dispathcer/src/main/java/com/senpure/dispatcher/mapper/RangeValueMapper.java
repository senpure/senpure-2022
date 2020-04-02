package com.senpure.dispatcher.mapper;

import com.senpure.dispatcher.model.RangeValue;
import com.senpure.dispatcher.criteria.RangeValueCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author senpure-generator
 * @version 2019-8-1 20:52:01
 */
@Mapper
@Repository
public interface RangeValueMapper {

    RangeValue find(Integer id);

    /**
     * 根据主键删除
     *
     * @return 影响的行数
     */
    int delete(Integer id);

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 影响的行数
     */
    int deleteByCriteria(RangeValueCriteria criteria);

    /**
     * 取对象的值，直接插入数据库(包括空值)
     * version字段(版本控制)，被初始化为1
     *
     * @return 影响的行数
     */
    int save(RangeValue rangeValue);

    /**
     * 取对象的值，直接插入数据库(包括空值)
     * version字段(版本控制)，被初始化为1
     *
     * @return 影响的行数
     */
    int saveBatch(List<RangeValue> rangeValues);

    /**
     * 会进行对象的空值判断，不为空才更新，以主键进行where判断
     * version字段(版本控制)，必须有有效值
     *
     * @return 影响的行数
     */
    int update(RangeValue rangeValue);

    /**
     * 直接将值覆盖到数据库，不会做为空判断，以主键进行where判断
     * version字段(版本控制)，必须有有效值
     *
     * @return 影响的行数
     */
    int cover(RangeValue rangeValue);

    /**
     * 会进行对象的空值判断，不为空才更新，主键无值时，可以进行批量更新
     *
     * @return 影响的行数
     */
    int updateByCriteria(RangeValueCriteria criteria);

    int count();

    List<RangeValue> findAll();

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 满足条件的总行数
     */
    int countByCriteria(RangeValueCriteria criteria);

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 满足条件的记录
     */
    List<RangeValue> findByCriteria(RangeValueCriteria criteria);
}
