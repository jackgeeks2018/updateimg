package com.wpp.dao;

import com.wpp.pojo.updateimg;
import com.wpp.pojo.updateimgExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface updateimgMapper {
    int countByExample(updateimgExample example);

    int deleteByExample(updateimgExample example);

    int insert(updateimg record);

    int insertSelective(updateimg record);

    List<updateimg> selectByExample(updateimgExample example);

    int updateByExampleSelective(@Param("record") updateimg record, @Param("example") updateimgExample example);

    int updateByExample(@Param("record") updateimg record, @Param("example") updateimgExample example);
}