
package com.wpp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QerallTest {
    @Select("select now()")
    public String QerallDate();

}
