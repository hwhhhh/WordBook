package com.hwhhhh.wordbook.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hwhhhh.wordbook.entity.Word;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    Context mContext;
    private SQLiteDatabase db;
    private String DB_PATH = "/data/data/com.hwhhhh.wordbook/databases/";
    private String DB_NAME = "word.db";

    public DBHelper(Context context) {
        this.mContext = context;
        initFile();
        db = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        LitePal.saveAll(this.getWords());
    }

    private List<Word> getWords() {
        List<Word> list = new ArrayList<>();
        //执行sql语句
        Cursor cursor = db.rawQuery("select * from englishwords", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            //遍历
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Word wordBean = new Word();
                wordBean.setWord(cursor.getString(cursor.getColumnIndex("word")));//题目内容
                wordBean.setPronunciation(cursor.getString(cursor.getColumnIndex("pronunciation")));//A答案
                wordBean.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));//B答案
                list.add(wordBean);
            }
        }
        cursor.close();
        return list;
    }

    private void initFile() {
        //判断数据库是否拷贝到相应的目录下
        if (!new File(DB_PATH + DB_NAME).exists()) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //复制文件
            try {
                InputStream is = mContext.getAssets().open("englishwords.db");
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
                byte[] buffer = new byte[1024];//用来复制文件
                int length;//保存已经复制的长度
                //开始复制
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                //刷新
                os.flush();
                //关闭
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
