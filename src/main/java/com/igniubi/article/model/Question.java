package com.igniubi.article.model;

import com.igniubi.mybatis.entity.BaseEntity;
import lombok.Data;

public class Question extends BaseEntity {

    private Integer id;

    private String qTitle;

    private String qDescribe;

    private String answers;

    private String dateId;

    private Integer createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqDescribe() {
        return qDescribe;
    }

    public void setqDescribe(String qDescribe) {
        this.qDescribe = qDescribe;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
