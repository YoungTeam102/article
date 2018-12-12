package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.MusicReq;

public interface IMusicService {

    CommonRsp addMusic(MusicReq req);
}
