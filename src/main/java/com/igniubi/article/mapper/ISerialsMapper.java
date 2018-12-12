package com.igniubi.article.mapper;

import com.igniubi.article.model.Question;
import com.igniubi.article.model.Serials;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface ISerialsMapper extends BaseMapper<Integer, Serials> {

    @Override
     int save(Serials var1);

    @Override
     int update(Serials var1);

    @Override
    int remove(Integer var1);


    @Override
    Serials get(Integer var1);

    void insertSerialsSelective(Serials serials);

    Serials selectSerialsByDate(@Param("dateId") String dateId);
}
