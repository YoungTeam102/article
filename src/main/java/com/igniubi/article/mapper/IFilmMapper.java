package com.igniubi.article.mapper;

import com.igniubi.article.model.Film;
import com.igniubi.article.model.Question;
import com.igniubi.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface IFilmMapper extends BaseMapper<Integer, Film> {

    @Override
     int save(Film var1);

    @Override
     int update(Film var1);

    @Override
    int remove(Integer var1);


    @Override
    Film get(Integer var1);

    void insertFilmSelective(Film film);

    Film selectFilmByDate(@Param("dateId") String dateId);
}
