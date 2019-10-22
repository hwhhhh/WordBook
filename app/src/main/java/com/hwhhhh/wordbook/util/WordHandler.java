package com.hwhhhh.wordbook.util;

import android.util.Log;

import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.WordInfo;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WordHandler extends DefaultHandler {
    private String tagName;
    private WordDto wordDto;
    private WordInfo wordInfo;
    private WordORIG wordORIG;
    private WordPartOfSpeech wordPartOfSpeech;
    private static final String TAG = "WordHandler";
    private StringBuffer str;

    public WordDto getWordDto() {
        return wordDto;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        wordDto = new WordDto();
        wordInfo = new WordInfo();
        wordORIG = new WordORIG();
        wordPartOfSpeech = new WordPartOfSpeech();
        str = new StringBuffer();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tagName = localName;
        str.delete(0, str.length());
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (!qName.equals("dict")) {
            if (!qName.equals("sent")) {
                String string = str.toString();
                string.trim();
                if (tagName.equals("key")) {
                    wordInfo.setKey(string);
                } else if (tagName.equals("ps")) {
                    if (wordInfo.getPsENG() == null) {
                        wordInfo.setPsENG(string);
                    } else {
                        wordInfo.setPsUS(string);
                    }
                } else if (tagName.equals("pron")) {
                    if (wordInfo.getPronPsENG() == null) {
                        wordInfo.setPronPsENG(string);
                    } else {
                        wordInfo.setPronPsUS(string);
                    }
                } else if (tagName.equals("pos")) {
                    if (wordPartOfSpeech.getPos() == null) {
                        wordPartOfSpeech.setPos(string);
                    }
                } else if (tagName.equals("acceptation")) {
                    if (wordPartOfSpeech.getAcceptation() == null) {
                        wordPartOfSpeech.setAcceptation(string);
                    }
                    wordDto.getWordPartOfSpeeches().add(wordPartOfSpeech);
                    wordPartOfSpeech = new WordPartOfSpeech();
                } else if (tagName.equals("orig")) {
                    if (wordORIG.getOrig() == null) {
                        wordORIG.setOrig(string);
                    }
                } else if (tagName.equals("trans")) {
                    if (wordORIG.getTrans() == null) {
                        wordORIG.setTrans(string);
                    }
                    wordDto.getWordORIGs().add(wordORIG);
                    wordORIG = new WordORIG();
                }
                wordDto.setWordInfo(wordInfo);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (ch[start] == '\n') {
            return;
        }
        str.append(ch, start, length);
    }
}
