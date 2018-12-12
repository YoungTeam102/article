package com.igniubi.article.service;

import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.FilmReq;

public interface IFilmService {

    CommonRsp addFilm(FilmReq req);
}
