package com.hwhhhh.wordbook.dto;

import com.hwhhhh.wordbook.entity.WordInfo;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordDto implements Serializable {
    private WordInfo wordInfo;
    private List<WordORIG> wordORIGs;
    private List<WordPartOfSpeech> wordPartOfSpeeches;

    public WordDto() {
        wordInfo = new WordInfo();
        wordORIGs = new ArrayList<>();
        wordPartOfSpeeches = new ArrayList<>();
    }

    public WordDto(WordInfo wordInfo, List<WordORIG> wordORIGs, List<WordPartOfSpeech> wordPartOfSpeeches) {
        this.wordInfo = wordInfo;
        this.wordORIGs = wordORIGs;
        this.wordPartOfSpeeches = wordPartOfSpeeches;
    }

    public WordInfo getWordInfo() {
        return wordInfo;
    }

    public void setWordInfo(WordInfo wordInfo) {
        this.wordInfo = wordInfo;
    }

    public List<WordORIG> getWordORIGs() {
        return wordORIGs;
    }

    public void setWordORIGs(List<WordORIG> wordORIGs) {
        this.wordORIGs = wordORIGs;
    }

    public List<WordPartOfSpeech> getWordPartOfSpeeches() {
        return wordPartOfSpeeches;
    }

    public void setWordPartOfSpeeches(List<WordPartOfSpeech> wordPartOfSpeeches) {
        this.wordPartOfSpeeches = wordPartOfSpeeches;
    }

    public String toString() {
        String str = wordInfo.toString();
        for (int i = 0; i < wordPartOfSpeeches.size(); i++) {
            str += wordPartOfSpeeches.get(i).toString();
        }
        for (int i = 0; i < wordORIGs.size(); i++) {
            str += wordORIGs.get(i).toString();
        }
        return str;
    }
}
