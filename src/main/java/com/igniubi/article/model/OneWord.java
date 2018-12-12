package com.igniubi.article.model;

import com.igniubi.mybatis.entity.BaseEntity;
import lombok.Data;

@Data
public class OneWord extends BaseEntity {

    private Integer id;

    private String imgSrc;

    private String word;

    private String wordsInfo;

    private String dateId;

    private Integer createTime;
}
