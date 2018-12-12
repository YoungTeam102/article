package com.igniubi.article.service.impl;

import com.igniubi.article.mapper.IFilmMapper;
import com.igniubi.article.model.Film;
import com.igniubi.article.service.IFilmService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.FilmReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilmServiceImpl implements IFilmService {

    private final Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class);

    @Autowired
    IFilmMapper filmMapper;

    @Override
    public CommonRsp addFilm( FilmReq req) {
        logger.info("enter addFilm , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getTitle()== null){
            logger.info("addFilm , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        Film film = filmMapper.selectFilmByDate(date);
        if(film != null){
            logger.info("addFilm , article is exist, dateId is {}", date);
           throw new IGNBException(402, "article is exist");
        }
        film = BeanUtils.copyBeans(Film.class , req);
        film.setDateId(date);
        film.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            filmMapper.insertFilmSelective(film);
            logger.info("addFilm success , req is {}", req);
        }catch (Exception e){
            logger.warn("addFilm failed , exception is {}", e);
        }
        return rsp;
    }
}
