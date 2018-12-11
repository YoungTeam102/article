package com.igniubi.article.mapper;

import com.igniubi.article.model.Question;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface IQuestionMapper extends BaseMapper<Integer, Question> {

    @Override
     int save(Question var1);

    @Override
     int update(Question var1);

    @Override
    int remove(Integer var1);


    @Override
    Question get(Integer var1);

    void insertQuestionSelective(Question article);

    Question selectQuestionByDate(@Param("dateId") String dateId);
}
