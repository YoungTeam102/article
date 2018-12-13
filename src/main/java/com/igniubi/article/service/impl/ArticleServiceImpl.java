package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.igniubi.article.common.TypeConstant;
import com.igniubi.article.mapper.*;
import com.igniubi.article.model.*;
import com.igniubi.article.restapi.ArticleRest;
import com.igniubi.article.service.IArticleService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import com.igniubi.model.article.rsp.ArticleRsp;
import com.igniubi.model.article.rsp.IndexRsp;
import com.igniubi.model.enums.common.RedisKeyEnum;
import com.igniubi.redis.operations.RedisValueOperations;
import com.igniubi.redis.util.RedisOperationsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleServiceImpl implements IArticleService {

    private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    IArticleMapper articleMapper;

    @Autowired
    IOneWordMapper oneWordMapper;

    @Autowired
    ISerialsMapper serialsMapper;

    @Autowired
    IMusicMapper musicMapper;

    @Autowired
    IFilmMapper filmMapper;

    @Autowired
    IQuestionMapper  questionMapper;

    @Autowired
    RedisValueOperations redisValueOperations;

    @Override
    public CommonRsp addArticle(ArticleReq req) {
        logger.info("enter addArticle , req is {}", req);
        CommonRsp rsp = new CommonRsp();
        if(req.getTitle()== null){
            logger.info("addArticle , req is null");
            throw new IGNBException(402, "req is null");
        }
        String date = DateUtil.getToday("YYYYMMdd");
        Article article = articleMapper.selectArticleByDate(date);
        if(article != null){
            logger.info("addArticle , article is exist, dateId is {}", date);
           throw new IGNBException(402, "article is exist");
        }
        article = BeanUtils.copyBeans(Article.class , req);
        article.setContent(req.getContent()==null ? "":JSONObject.toJSONString(req.getContent()));
        article.setDateId(date);
        article.setCreateTime(DateUtil.getCurrentTimeSeconds());
        try{
            articleMapper.insertArticleSelective(article);
            logger.info("addArticle success , req is {}", req);
        }catch (Exception e){
            logger.warn("addArticle failed , exception is {}", e);
        }
        return rsp;
    }

    @Override
    public  List<IndexRsp> getIndex(ArticleReq req) {
        if(req.getDate()== null){
            logger.info("getIndex , date is null");
            throw new IGNBException(402, "date is null");
        }
        List<IndexRsp> rspList = new ArrayList<>();
        Article article = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_,req.getDate(),
                () ->articleMapper.selectArticleByDate(req.getDate()),Article.class);
        OneWord word = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_OW,req.getDate(),
                () ->oneWordMapper.selectWordByDate(req.getDate()),OneWord.class);
        Serials serials = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_SERIALS,req.getDate(),
                () ->serialsMapper.selectSerialsByDate(req.getDate()),Serials.class);
        Question question = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_QA,req.getDate(),
                () ->questionMapper.selectQuestionByDate(req.getDate()),Question.class);
        Music music = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_MUSIC,req.getDate(),
                () ->musicMapper.selectMusicByDate(req.getDate()),Music.class);
        Film film = RedisOperationsUtil.cacheObtain(redisValueOperations, RedisKeyEnum.ARTICLE_FILM,req.getDate(),
                () ->filmMapper.selectFilmByDate(req.getDate()),Film.class);
        if (article !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, article);
            rsp.setType(TypeConstant.ARTICLE);
            rspList.add(rsp);
        }
        if (word !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, word);
            rsp.setForward(word.getWord());
            rsp.setAuthor(word.getWordsInfo());
            rsp.setImgUrl(word.getImgSrc());
            rsp.setType(TypeConstant.ONE_WORD);
            rspList.add(rsp);
        }
        if (serials !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, serials);
            rsp.setType(TypeConstant.SERIALS);
            rspList.add(rsp);
        }
        if (question !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, question);
            rsp.setTitle(question.getqTitle());
            rsp.setAuthor(question.getAnswerer());
            rsp.setForward(question.getqDescribe());
            rsp.setType(TypeConstant.QUESTION);
            rspList.add(rsp);
        }
        if (music !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, music);
            rsp.setType(TypeConstant.MUSIC);
            rspList.add(rsp);
        }
        if(film !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, film);
            rsp.setType(TypeConstant.FILM);
            rspList.add(rsp);
        }
        return rspList;
    }
}
