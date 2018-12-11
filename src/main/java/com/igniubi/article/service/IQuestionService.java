package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.ArticleReq;
import com.igniubi.model.article.req.QuestionReq;

public interface IQuestionService {

    CommonRsp addQuestion(QuestionReq req);
}
