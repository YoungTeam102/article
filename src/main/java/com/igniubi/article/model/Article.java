package com.igniubi.article.model;

import com.igniubi.mybatis.entity.BaseEntity;
import lombok.Data;

@Data
public class Article extends BaseEntity {

    private Integer id;

    private String title;

    private String auther;

    private String content;

    private String dateId;

    private Integer createTime;
}
