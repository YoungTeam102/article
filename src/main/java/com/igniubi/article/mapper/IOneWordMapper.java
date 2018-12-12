package com.igniubi.article.mapper;

import com.igniubi.article.model.Article;
import com.igniubi.article.model.OneWord;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface IOneWordMapper extends BaseMapper<Integer, OneWord> {

    @Override
     int save(OneWord var1);

    @Override
     int update(OneWord var1);

    @Override
    int remove(Integer var1);


    @Override
    OneWord get(Integer var1);

    void insertWordSelective(OneWord oneWord);

    OneWord selectWordByDate(@Param("dateId") String dateId);
}
