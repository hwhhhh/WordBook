package com.hwhhhh.wordbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.entity.WordORIG;

import java.util.List;

public class WordORIGAdapter extends BaseAdapter {
    private Context mContext;
    private List<WordORIG> wordORIGS;

    public WordORIGAdapter(Context context, List<WordORIG> wordORIGS) {
        this.mContext = context;
        this.wordORIGS = wordORIGS;
    }

    @Override
    public int getCount() {
        return wordORIGS.size();
    }

    @Override
    public Object getItem(int i) {
        return wordORIGS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        WordORIG wordORIG = (WordORIG) getItem(i);
        View contentView;
        ViewHolder viewHolder;
        if (view == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_word_orig, null);
            viewHolder = new ViewHolder();
            viewHolder.orig = contentView.findViewById(R.id.orig_item_eng);
            viewHolder.trans = contentView.findViewById(R.id.orig_item_chn);
            contentView.setTag(viewHolder);
        } else {
            contentView = view;
            viewHolder = (ViewHolder) contentView.getTag();
        }
        viewHolder.orig.setText(wordORIG.getOrig());
        viewHolder.trans.setText(wordORIG.getTrans());
        return contentView;
    }

    class ViewHolder {
        TextView orig;
        TextView trans;
    }
}
