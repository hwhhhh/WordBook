package com.hwhhhh.wordbook.entity;

import org.litepal.crud.LitePalSupport;

public class WordPartOfSpeech extends LitePalSupport {

    private int id;
    private int wordId;
    //词性
    private String pos;
    //释义
    private String acceptation;

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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
    }

    public String toString() {
        return "\n" + pos + "  " + acceptation;
    }
}
