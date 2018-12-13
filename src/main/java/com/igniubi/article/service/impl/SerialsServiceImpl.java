package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.igniubi.article.mapper.ISerialsMapper;
import com.igniubi.article.model.Serials;
import com.igniubi.article.service.ISerialsService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.SerialsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SerialsServiceImpl implements ISerialsService {

    private final Logger logger = LoggerFactory.getLogger(SerialsServiceImpl.class);

    @Autowired
    ISerialsMapper serialsMapper;

    @Override
    public CommonRsp addSerials(SerialsReq req) {
        logger.info("enter addSerials , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getTitle()== null){
            logger.info("addSerials , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        Serials serials = serialsMapper.selectSerialsByDate(date);
        if(serials != null){
            logger.info("addSerials , article is exist, dateId is {}", date);
           throw new IGNBException(402, "article is exist");
        }
        serials = BeanUtils.copyBeans(Serials.class , req);
        serials.setDateId(date);
        serials.setContent(req.getContent()==null ? "":JSONObject.toJSONString(req.getContent()));
        serials.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            serialsMapper.insertSerialsSelective(serials);
            logger.info("addSerials success , req is {}", req);
        }catch (Exception e){
            logger.warn("addSerials failed , exception is {}", e);
        }
        return rsp;
    }
}
