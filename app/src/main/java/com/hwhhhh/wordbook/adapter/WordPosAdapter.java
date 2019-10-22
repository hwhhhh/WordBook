package com.hwhhhh.wordbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

import java.util.List;

public class WordPosAdapter extends BaseAdapter {
    private static final String TAG = "WordPosAdapter";
    private List<WordPartOfSpeech> wordPartOfSpeeches;
    private Context mContext;

    public WordPosAdapter(Context context, List<WordPartOfSpeech> wordPartOfSpeeches) {
        this.mContext = context;
        this.wordPartOfSpeeches = wordPartOfSpeeches;
    }

    @Override
    public int getCount() {
        return wordPartOfSpeeches.size();
    }

    @Override
    public Object getItem(int i) {
        return wordPartOfSpeeches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contentView;
        WordPartOfSpeech wordPartOfSpeech = (WordPartOfSpeech) getItem(i);
        ViewHolder viewHolder;
        if (view == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_word_pos, null);
            viewHolder = new ViewHolder();
            viewHolder.pos = contentView.findViewById(R.id.pos_item_pos);
            viewHolder.acceptation = contentView.findViewById(R.id.pos_item_acceptation);
            contentView.setTag(viewHolder);
        } else {
            contentView = view;
            viewHolder = (ViewHolder) contentView.getTag();
        }
        viewHolder.pos.setText(wordPartOfSpeech.getPos());
        viewHolder.acceptation.setText(wordPartOfSpeech.getAcceptation());
        return contentView;
    }

    class ViewHolder {
        TextView pos;
        TextView acceptation;
    }
}
