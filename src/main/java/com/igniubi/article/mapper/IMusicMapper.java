package com.igniubi.article.mapper;

import com.igniubi.article.model.Music;
import com.igniubi.article.model.Question;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface IMusicMapper extends BaseMapper<Integer, Music> {

    @Override
     int save(Music var1);

    @Override
     int update(Music var1);

    @Override
    int remove(Integer var1);


    @Override
    Music get(Integer var1);

    void insertMusicSelective(Music music);

    Music selectMusicByDate(@Param("dateId") String dateId);
}
