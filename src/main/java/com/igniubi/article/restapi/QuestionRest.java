package com.igniubi.article.restapi;

import com.igniubi.article.service.IQuestionService;
import com.igniubi.model.CommonRsp;
import com.igniubi.model.article.req.QuestionReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionRest {

    private final Logger logger = LoggerFactory.getLogger(QuestionRest.class);

    @Autowired
    IQuestionService questionService;


    @RequestMapping("/addQuestion")
    public CommonRsp addQuestion(@RequestBody QuestionReq req){
        return  questionService.addQuestion(req);
    }
}
