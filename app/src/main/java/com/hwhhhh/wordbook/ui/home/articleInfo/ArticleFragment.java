package com.hwhhhh.wordbook.ui.home.articleInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwhhhh.wordbook.R;

public class ArticleFragment extends Fragment {
    private static ArticleFragment articleFragment;
    private static final String TAG = "ArticleFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ArticleFragment");
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ArticleFragment");
    }

    public static ArticleFragment getInstance() {
        if (articleFragment == null) {
            synchronized (ArticleFragment.class) {
                if (articleFragment == null) {
                    articleFragment = new ArticleFragment();
                }
            }
        }
        return articleFragment;
    }
}
