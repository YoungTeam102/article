package com.igniubi.article.restapi;

import com.igniubi.article.service.ISerialsService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.SerialsReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerialsRest {

    private final Logger logger = LoggerFactory.getLogger(SerialsRest.class);

    @Autowired
    ISerialsService serialsService;


    @RequestMapping("/addSerials")
    public CommonRsp addSerials(@RequestBody SerialsReq req){
        return  serialsService.addSerials(req);
    }
}
