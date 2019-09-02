package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.igniubi.article.common.TypeConstant;
import com.igniubi.article.mapper.*;
import com.igniubi.article.model.*;
import com.igniubi.article.service.IArticleService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import com.igniubi.model.article.rsp.IndexRsp;
import com.igniubi.model.enums.common.RedisKeyEnum;
import com.igniubi.redis.operations.RedisValueOperations;
import com.igniubi.redis.util.RedisKeyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    public  List<IndexRsp> getIndex(ArticleReq req) throws ExecutionException, InterruptedException {
        logger.info("begin getIndex");
        if(req.getDate()== null){
            logger.info("getIndex , date is null");
            throw new IGNBException(402, "date is null");
        }
        List<IndexRsp> rspList = new ArrayList<>();
        Future<Article> article = getFromCacheOrDb( RedisKeyEnum.ARTICLE_,req.getDate(),
                () ->articleMapper.selectArticleByDate(req.getDate()),Article.class);
        Future<OneWord> word = getFromCacheOrDb( RedisKeyEnum.ARTICLE_OW,req.getDate(),
                () ->oneWordMapper.selectWordByDate(req.getDate()),OneWord.class);
        Future<Serials> serials = getFromCacheOrDb( RedisKeyEnum.ARTICLE_SERIALS,req.getDate(),
                () ->serialsMapper.selectSerialsByDate(req.getDate()),Serials.class);
        Future<Question> question = getFromCacheOrDb(RedisKeyEnum.ARTICLE_QA,req.getDate(),
                () ->questionMapper.selectQuestionByDate(req.getDate()),Question.class);
        Future<Music> music =getFromCacheOrDb(RedisKeyEnum.ARTICLE_MUSIC,req.getDate(),
                () ->musicMapper.selectMusicByDate(req.getDate()),Music.class);
        Future<Film> film = getFromCacheOrDb( RedisKeyEnum.ARTICLE_FILM,req.getDate(),
                () ->filmMapper.selectFilmByDate(req.getDate()),Film.class);
        if (article !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, article.get());
            rsp.setType(TypeConstant.ARTICLE);
            rspList.add(rsp);
        }
        if (word !=null){
            OneWord o = word.get();
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, o);
            rsp.setForward(o.getWord());
            rsp.setAuthor(o.getWordsInfo());
            rsp.setImgUrl(o.getImgSrc());
            rsp.setType(TypeConstant.ONE_WORD);
            rspList.add(rsp);
        }
        if (serials !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, serials.get());
            rsp.setType(TypeConstant.SERIALS);
            rspList.add(rsp);
        }
        if (question !=null){
            Question question1 = question.get();
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, question1);
            rsp.setTitle(question1.getqTitle());
            rsp.setAuthor(question1.getAnswerer());
            rsp.setForward(question1.getqDescribe());
            rsp.setType(TypeConstant.QUESTION);
            rspList.add(rsp);
        }
        if (music !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, music.get());
            rsp.setType(TypeConstant.MUSIC);
            rspList.add(rsp);
        }
        if(film !=null){
            IndexRsp rsp = BeanUtils.copyBeans(IndexRsp.class, film.get());
            rsp.setType(TypeConstant.FILM);
            rspList.add(rsp);
        }
        logger.info("end getIndex");

        return rspList;
    }


    @Async
    <T> Future<T> getFromCacheOrDb(RedisKeyEnum keyEnum, Object key, Callable<T> callable, Class<T> c){
        RedisKeyBuilder keyBuilder = RedisKeyBuilder.newInstance().appendFixed(keyEnum.getCacheKey()).appendVar(key);
        T t =  redisValueOperations.get(keyBuilder, c);

        logger.info(" cacheObtain from cache success, key is {} " , keyBuilder.getKey() );
        if(t != null){
            return AsyncResult.forValue(t);
        }

        try {
            t = callable.call();
            logger.info(" cacheObtain from callable success, key is {}" , keyBuilder.getKey());
        } catch (Exception e) {
            logger.info("cacheObtain error, e is {}", e);
        }

        if(t != null ){
            redisValueOperations.set(keyBuilder, t, keyEnum.getCacheTime(), keyEnum.getTimeUnit());
        }
        return t==null ? null : AsyncResult.forValue(t);
    }
}
