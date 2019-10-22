package com.hwhhhh.wordbook.util;

import android.util.Log;
import android.widget.ListView;

import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.Word;

import java.io.InputStream;
import java.util.List;

/**
 * EventBus 消息事件类，发送httpRequest请求，获得WordDto信息。
 */
public class MessageEvent {
    private static final String TAG = "MessageEvent";
    private WordDto wordDto;
    private List<Word> words;

    public MessageEvent(WordDto wordDto) {
        this.wordDto = wordDto;
    }

    public MessageEvent (List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public WordDto getWordDto() {
        return wordDto;
    }

    public void setWordDto(WordDto wordDto) {
        this.wordDto = wordDto;
    }
}
