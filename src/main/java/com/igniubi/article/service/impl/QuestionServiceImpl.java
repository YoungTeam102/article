package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.igniubi.article.mapper.IQuestionMapper;
import com.igniubi.article.model.Question;
import com.igniubi.article.restapi.ArticleRest;
import com.igniubi.article.service.IQuestionService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.QuestionReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionServiceImpl implements IQuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Autowired
    IQuestionMapper questionMapper;

    @Override
    public CommonRsp addQuestion(QuestionReq req) {
        logger.info("enter addQuestion , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getqTitle()== null){
            logger.info("addQuestion , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        Question question = questionMapper.selectQuestionByDate(date);
        if(question != null){
            logger.info("addQuestion , question is exist, dateId is {}", date);
           throw new IGNBException(402, "question is exist");
        }
        question = BeanUtils.copyBeans(Question.class , req);
        question.setAnswers(req.getAnswers() == null ? "" : JSON.toJSONString(req.getAnswers()));
        question.setDateId(date);
        question.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            questionMapper.insertQuestionSelective(question);
            logger.info("addQuestion success , req is {}", req);
        }catch (Exception e){
            logger.warn("addQuestion failed , exception is {}", e);
        }
        return rsp;
    }
}
