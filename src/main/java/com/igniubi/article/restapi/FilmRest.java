package com.igniubi.article.restapi;

import com.igniubi.article.service.IFilmService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.FilmReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmRest {

    private final Logger logger = LoggerFactory.getLogger(FilmRest.class);

    @Autowired
    IFilmService filmService;


    @RequestMapping("/addFilm")
    public CommonRsp addFilm(@RequestBody FilmReq req){
        return  filmService.addFilm(req);
    }
}
