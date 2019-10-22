package com.hwhhhh.wordbook.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class WordInfo extends LitePalSupport {
    //primary key
    private int id;
    //单词
    @Column(unique = true)
    private String key;
    //英式发音
    private String psENG;
    //英式发音地址
    private String pronPsENG;
    //美式发音
    private String psUS;
    //美式发音地址
    private String pronPsUS;
//    //判断是否为中文
//    private boolean isChinese;
    @Column(defaultValue = "0")
    private int like;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPsENG() {
        return psENG;
    }

    public void setPsENG(String psENG) {
        this.psENG = psENG;
    }

    public String getPronPsENG() {
        return pronPsENG;
    }

    public void setPronPsENG(String pronPsENG) {
        this.pronPsENG = pronPsENG;
    }

    public String getPsUS() {
        return psUS;
    }

    public void setPsUS(String psUS) {
        this.psUS = psUS;
    }

    public String getPronPsUS() {
        return pronPsUS;
    }

    public void setPronPsUS(String pronPsUS) {
        this.pronPsUS = pronPsUS;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String toString() {
        return "key=" + key +"\tpsENG" + psENG +  "\tpsUS" + psUS;
    }
}
