package com.hwhhhh.wordbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.entity.Word;

import java.util.List;

public class WordSearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<Word> words;

    public WordSearchAdapter(Context mContext, List<Word> words) {
        this.mContext = mContext;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return words.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mView;
        Word word = words.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.item_search_fuzzy, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = mView.findViewById(R.id.item_search_word);
            mView.setTag(viewHolder);
        } else {
            mView = view;
            viewHolder = (ViewHolder) mView.getTag();
        }
        viewHolder.textView.setText(word.getWord());
        return mView;
    }

    private class ViewHolder {
        TextView textView;
    }
}
