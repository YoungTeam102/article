package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.WordReq;

public interface IWordService {

    CommonRsp addOneWord(WordReq req);
}
