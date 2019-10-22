package com.hwhhhh.wordbook.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.litepal.LitePal;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    private static final String TAG = "MyContentProvider";

    public static final int WORD_DIR = 0;
    public static final int WORD_ITEM = 1;
    public static final String AUTHORITY = "com.hwhhhh.wordbook.provider";
    private static UriMatcher uriMatcher;
    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "word", WORD_DIR);
        uriMatcher.addURI(AUTHORITY, "word/*", WORD_ITEM);
    }

    @Override
    public boolean onCreate() {
        db = LitePal.getDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String s, @Nullable String[] sArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                cursor = db.query("Word", projection, s, sArgs, null, null, sortOrder);
                break;
            case WORD_ITEM:
                String word = uri.getPathSegments().get(1);
                cursor = db.query("Word", projection, "word = ?", new String[] { word }, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                return "vnd.android.cursor.dir/vnd.com.hwhhhh.wordbook.provider.word";
            case WORD_ITEM:
                return "vnd.android.cursor.item/vnd.com.hwhhhh.wordbook.provider.word";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri uriBean = null;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
            case WORD_ITEM:
                db.insert("Word", null, contentValues);
                if (contentValues.get("word") != null) {
                    uriBean = Uri.parse("content://" + AUTHORITY + "/word/" + contentValues.get("word"));
                }
                Log.d(TAG, "insert: " + uriBean);
                break;
            default:
                break;
        }
        return uriBean;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                deleteRows = db.delete("Word", s, strings);
                break;
            case WORD_ITEM:
                String word = uri.getPathSegments().get(1);
                deleteRows = db.delete("Word", "word = ?", new String[] { word });
                Log.d(TAG, "delete: " + deleteRows);
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updateRows = 0;
        Log.d(TAG, "update: " + uri);
        Log.d(TAG, "update: " + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case WORD_DIR:
                updateRows = db.update("Word", contentValues, s, strings);
                break;
            case WORD_ITEM:
                String word = uri.getPathSegments().get(1);
                Log.d(TAG, "update: " + word);
                updateRows = db.update("Word", contentValues, "word = ?", new String[] { word });
                Log.d(TAG, "update: " + updateRows);
                break;
            default:
                break;
        }
        return updateRows;
    }
}
