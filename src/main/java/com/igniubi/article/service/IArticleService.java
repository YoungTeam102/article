package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;

public interface IArticleService {

    CommonRsp addArticle(ArticleReq req);
}
