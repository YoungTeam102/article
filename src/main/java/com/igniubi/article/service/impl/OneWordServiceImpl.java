package com.igniubi.article.service.impl;

import com.igniubi.article.mapper.IOneWordMapper;
import com.igniubi.article.model.OneWord;
import com.igniubi.article.restapi.ArticleRest;
import com.igniubi.article.service.IWordService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.WordReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OneWordServiceImpl implements IWordService {

    private final Logger logger = LoggerFactory.getLogger(OneWordServiceImpl.class);

    @Autowired
    IOneWordMapper oneWordMapper;

    @Override
    public CommonRsp addOneWord(WordReq req) {
        logger.info("enter addOneWord , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getImgSrc()== null){
            logger.info("addOneWord , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        OneWord word = oneWordMapper.selectWordByDate(date);
        if(word != null){
            logger.info("addOneWord , word is exist, dateId is {}", date);
           throw new IGNBException(402, "word is exist");
        }
        word = BeanUtils.copyBeans(OneWord.class , req);
        word.setDateId(date);
        word.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            oneWordMapper.insertWordSelective(word);
            logger.info("addOneWord success , req is {}", req);
        }catch (Exception e){
            logger.warn("addOneWord failed , exception is {}", e);
        }
        return rsp;
    }
}
