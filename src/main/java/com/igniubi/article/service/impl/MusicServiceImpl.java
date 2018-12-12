package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.igniubi.article.mapper.IMusicMapper;
import com.igniubi.article.model.Music;
import com.igniubi.article.service.IMusicService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.MusicReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicServiceImpl implements IMusicService {

    private final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);

    @Autowired
    IMusicMapper musicMapper;

    @Override
    public CommonRsp addMusic(MusicReq req) {
        logger.info("enter addMusic , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getTitle()== null){
            logger.info("addMusic , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        Music music = musicMapper.selectMusicByDate(date);
        if(music != null){
            logger.info("addMusic , article is exist, dateId is {}", date);
           throw new IGNBException(402, "article is exist");
        }
        music = BeanUtils.copyBeans(Music.class , req);
        music.setDateId(date);
        music.setMusicInfo(req.getMusicInfo()==null ? "":JSON.toJSONString(req.getMusicInfo()));
        music.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            musicMapper.insertMusicSelective(music);
            logger.info("addMusic success , req is {}", req);
        }catch (Exception e){
            logger.warn("addMusic failed , exception is {}", e);
        }
        return rsp;
    }
}
