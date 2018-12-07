package com.igniubi.article.restapi;

import com.igniubi.article.service.IArticleService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRest {

    private final Logger logger = LoggerFactory.getLogger(ArticleRest.class);

    @Autowired
    IArticleService articleService;


    @RequestMapping("/addArticle")
    public CommonRsp addArticle(@RequestBody ArticleReq req){
        return  articleService.addArticle(req);
    }
}
