package com.igniubi.article.restapi;

import com.igniubi.article.service.IMusicService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.MusicReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicRest {

    private final Logger logger = LoggerFactory.getLogger(MusicRest.class);

    @Autowired
    IMusicService musicService;


    @RequestMapping("/addMusic")
    public CommonRsp addMusic(@RequestBody MusicReq req){
        return  musicService.addMusic(req);
    }
}
