package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.SerialsReq;

public interface ISerialsService {

    CommonRsp addSerials(SerialsReq req);
}
