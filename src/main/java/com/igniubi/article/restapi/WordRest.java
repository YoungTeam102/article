package com.igniubi.article.restapi;

import com.igniubi.article.service.IWordService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.WordReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordRest {

    private final Logger logger = LoggerFactory.getLogger(WordRest.class);

    @Autowired
    IWordService wordService;


    @RequestMapping("/addOneWord")
    public CommonRsp addOneWord(@RequestBody WordReq req){
        return  wordService.addOneWord(req);
    }
}
