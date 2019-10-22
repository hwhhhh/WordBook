package com.hwhhhh.wordbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hwhhhh.wordbook.entity.WordInfo;

import java.util.List;

public class WordListAdapter extends BaseAdapter {
    private List<WordInfo> wordInfoList;
    private Context mContext;

    public WordListAdapter(Context context, List<WordInfo> wordInfos) {
        this.mContext = context;
        this.wordInfoList = wordInfos;
    }

    @Override
    public int getCount() {
        return wordInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return wordInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
