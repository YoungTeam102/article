package com.igniubi.article.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.igniubi.article.mapper.IArticleMapper;
import com.igniubi.article.model.Article;
import com.igniubi.article.restapi.ArticleRest;
import com.igniubi.article.service.IArticleService;
import com.igniubi.common.exceptions.IGNBException;
import com.igniubi.common.utils.BeanUtils;
import com.igniubi.common.utils.DateUtil;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleServiceImpl implements IArticleService {

    private final Logger logger = LoggerFactory.getLogger(ArticleRest.class);

    @Autowired
    IArticleMapper articleMapper;

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
}
