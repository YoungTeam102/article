package com.igniubi.article.mapper;

import com.igniubi.article.model.Article;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface  IArticleMapper extends BaseMapper<Integer, Article> {

    @Override
     int save(Article var1);

    @Override
     int update(Article var1);

    @Override
    int remove(Integer var1);


    @Override
     Article get(Integer var1);

    void insertArticleSelective(Article article);

    Article selectArticleByDate(@Param("dateId") String dateId);
}
