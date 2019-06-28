package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import com.igniubi.model.article.rsp.ArticleRsp;
import com.igniubi.model.article.rsp.IndexRsp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IArticleService {

    CommonRsp addArticle(ArticleReq req);

    List<IndexRsp> getIndex(ArticleReq req) throws ExecutionException, InterruptedException;
}
