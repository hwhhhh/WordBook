package com.hwhhhh.wordbook.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Word extends LitePalSupport {
    @Column(unique = true)
    private String word;
    private String pronunciation;
    private String meaning;

    public Word(){

    }

    public Word(String word, String pronunciation, String meaning) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String toString() {
        return this.word;
    }
}
