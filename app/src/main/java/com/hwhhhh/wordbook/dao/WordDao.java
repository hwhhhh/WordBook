package com.hwhhhh.wordbook.dao;

import android.content.ContentValues;
import android.util.Log;

import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.Word;
import com.hwhhhh.wordbook.entity.WordInfo;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class WordDao {
    private static WordDao wordDao;
    private static final String TAG = "WordDao";

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
        WordInfo wordInfo = wordDto.getWordInfo();
        if (!wordInfo.save()) {
            return false;
        }
        for (int i = 0; i < wordDto.getWordPartOfSpeeches().size(); i++) {
            WordPartOfSpeech wordPartOfSpeech = wordDto.getWordPartOfSpeeches().get(i);
            wordPartOfSpeech.setWordId(wordInfo.getId());
            if (!wordPartOfSpeech.save()) {
                wordInfo.delete();
                for (int j = i - 1; j >= 0; j --) {
                    wordDto.getWordPartOfSpeeches().get(i).delete();
                }
                return false;
            }
        }

        for (int i = 0; i < wordDto.getWordORIGs().size(); i++) {
            WordORIG wordORIG = wordDto.getWordORIGs().get(i);
            wordORIG.setWordId(wordInfo.getId());
            if (!wordORIG.save()) {
                for (int j = i - 1; j >= 0; j --) {
                    wordDto.getWordORIGs().get(j).delete();
                }
                for (int j = 0; j < wordDto.getWordPartOfSpeeches().size(); j ++) {
                    wordDto.getWordPartOfSpeeches().get(j).delete();
                }
                return false;
            }
        }
        return true;
    }

    public List<Word> searchWord(String str) {
        List<Word> words = LitePal
                .where("word like ?", str + "%")
                .order("word asc")
                .limit(20)
                .find(Word.class);
        Log.d(TAG, "searchWord: " + words);
        return words;
    }

    public boolean isSaved(WordDto wordDto) {
        List<WordInfo> wordInfos = LitePal.where("key = ?", wordDto.getWordInfo().getKey()).find(WordInfo.class);
        return wordInfos.size() > 0;
    }

    public void delete(WordDto wordDto) {
        List<WordInfo> wordInfos = LitePal.where("key = ?", wordDto.getWordInfo().getKey()).find(WordInfo.class);
        String id = wordInfos.get(0).getId() + "";
        LitePal.deleteAll(WordPartOfSpeech.class, "wordId = ?", id);
        LitePal.deleteAll(WordORIG.class, "wordId = ?", id);
        LitePal.delete(WordInfo.class, wordInfos.get(0).getId());
    }

    public int delete(Word word) {
        return LitePal.deleteAll(Word.class, "word = ?", word.getWord());
    }

    public boolean add(String word, String pronunciation, String meaning) {
        Word word1 = new Word(word, pronunciation, meaning);
        return word1.save();
    }

    public boolean modify(String word, String pron, String acce) {
        ContentValues values = new ContentValues();
        values.put("pronunciation", pron);
        values.put("meaning", acce);
        return LitePal.updateAll(Word.class, values, "word = ?", word) > 0;
    }
}
