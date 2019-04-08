package com.qujie.mintwo.base.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseDao<T>  extends BaseMapper<T> {

    /**
     * 查询 返回map
     * @param findSql
     * @return
     */
    List<Map> findBySql(@Param("findSql") String findSql);
    /**
     * 插入数据到mysql相应的表中
     * @param dataMap
     */
    void insertData(@Param("dataMap") Map<String, String> dataMap);

}
