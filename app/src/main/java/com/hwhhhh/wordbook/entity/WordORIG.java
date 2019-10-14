package com.hwhhhh.wordbook.entity;

import org.litepal.crud.LitePalSupport;

public class WordORIG extends LitePalSupport {
    private int id;
    private int wordId;
    //例句
    private String orig;
    //例句翻译
    private String trans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String toString() {
        return "\neg:" + orig + "\n翻译:" + trans;
    }
}
