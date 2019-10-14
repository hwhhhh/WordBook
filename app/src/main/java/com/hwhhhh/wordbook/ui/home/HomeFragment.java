package com.hwhhhh.wordbook.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.dao.WordDao;
import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.ui.home.articleInfo.ArticleFragment;
import com.hwhhhh.wordbook.ui.home.wordInfo.WordInfoFragment;
import com.hwhhhh.wordbook.util.HttpCallBackListener;
import com.hwhhhh.wordbook.util.HttpUtil;
import com.hwhhhh.wordbook.util.ParseXML;
import com.hwhhhh.wordbook.util.WordHandler;

import java.io.InputStream;

public class HomeFragment extends Fragment {
    private static HomeFragment homeFragment;
    private static final String TAG = "HomeFragment";
    private FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: HomeFragment");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = getChildFragmentManager();
        final EditText editText = getActivity().findViewById(R.id.home_edit_text);
        Button button_search = getActivity().findViewById(R.id.home_button_search);
        initFragmentArticle();
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = editText.getText().toString();
                search(word);
            }
        });
    }


    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            synchronized (HomeFragment.class) {
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
            }
        }
        return homeFragment;
    }

    private void initFragmentArticle() {
        ArticleFragment articleFragment = ArticleFragment.getInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //添加articleFragment
        if (!articleFragment.isAdded()) {
            fragmentTransaction
                    .add(R.id.home_fragment_container, articleFragment, articleFragment.getClass().getName());
        }
        fragmentTransaction.commit();
        Log.d(TAG, "initFragmentArticle: ");
    }

    private void initFragmentWord(Bundle bundle) {
        WordInfoFragment wordInfoFragment = WordInfoFragment.getInstance();
        wordInfoFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (wordInfoFragment.isAdded()) {
            fragmentTransaction.show(wordInfoFragment);
        } else {
            fragmentTransaction
                    .hide(ArticleFragment.getInstance())
                    .add(R.id.home_fragment_container, wordInfoFragment, wordInfoFragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    private void search(String word) {
        String url = WordDao.getInstance().getUrl(word);
        Log.d(TAG, "search: url" + url);
        HttpUtil.setHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(InputStream inputStream) {
                WordHandler wordHandler = new WordHandler();
                ParseXML.parse(wordHandler, inputStream);
                callBack(wordHandler.getWordDto());
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void callBack(WordDto wordDto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", wordDto);
        initFragmentWord(bundle);
    }
}
