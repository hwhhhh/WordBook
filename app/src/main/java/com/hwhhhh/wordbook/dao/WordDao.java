package com.hwhhhh.wordbook.dao;

import android.util.Log;

import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.WordInfo;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

public class WordDao {
    private static WordDao wordDao;
    private static final String TAG = "WordDao";
    private WordInfo wordInfo;
    private WordORIG wordORIG;
    private WordPartOfSpeech wordPartOfSpeech;

    public static WordDao getInstance() {
        if (wordDao == null) {
            synchronized (WordDao.class) {
                if (wordDao == null) {
                    wordDao = new WordDao();
                }
            }
        }
        return wordDao;
    }

    public boolean saveWord(WordDto wordDto) {
        wordInfo = wordDto.getWordInfo();
        if (!wordInfo.save()) {
            return false;
        }
        for (int i = 0; i < wordDto.getWordORIGs().size(); i++) {
            wordORIG = wordDto.getWordORIGs().get(i);
            wordORIG.setWordId(wordInfo.getId());
            if (!wordDto.getWordORIGs().get(i).save()) {
                return false;
            }
        }
        for (int i = 0; i < wordDto.getWordPartOfSpeeches().size(); i++) {
            wordPartOfSpeech = wordDto.getWordPartOfSpeeches().get(i);
            wordPartOfSpeech.setWordId(wordInfo.getId());
            if (!wordPartOfSpeech.save()) {
                return false;
            }
        }
        return true;
    }

    public String getUrl(String word) {
        String url = "https://dict-co.iciba.com/api/dictionary.php?key=7543E04062DE5B2E5370A0627D59F529&w=";
        Log.d(TAG, "getUrl: " + url + word);
        return url + word;
    }
}
