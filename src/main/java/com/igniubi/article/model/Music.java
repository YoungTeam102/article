package com.igniubi.article.model;

import com.igniubi.mybatis.entity.BaseEntity;
import lombok.Data;

@Data
public class Music extends BaseEntity {

    private Integer id;

    private String title;

    private String author;

    private String content;

    private String contentTitle;

    private String dateId;

    private Integer createTime;

    private String forward;

    private String imgUrl;

    private String coverImgUrl;

    private String musicUrl;

    private String musicInfo;
}
