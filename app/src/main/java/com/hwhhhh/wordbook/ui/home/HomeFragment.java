package com.hwhhhh.wordbook.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.dao.WordDao;
import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.Word;
import com.hwhhhh.wordbook.ui.home.articleInfo.ArticleFragment;
import com.hwhhhh.wordbook.ui.home.search.SearchFragment;
import com.hwhhhh.wordbook.ui.home.wordInfo.LocalWordFragment;
import com.hwhhhh.wordbook.ui.home.wordInfo.WordInfoFragment;
import com.hwhhhh.wordbook.util.HttpCallBackListener;
import com.hwhhhh.wordbook.util.HttpUtil;
import com.hwhhhh.wordbook.util.MessageEvent;
import com.hwhhhh.wordbook.util.ParseXML;
import com.hwhhhh.wordbook.util.WordHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HomeFragment extends Fragment {
    private static HomeFragment homeFragment;
    private static final String TAG = "HomeFragment";
    private FragmentManager fragmentManager;
    private WordDto wordDto;
    private CountDownLatch countDownLatch;
    private ArticleFragment articleFragment = ArticleFragment.getInstance();
    private WordInfoFragment wordInfoFragment = WordInfoFragment.getInstance();
    private SearchFragment searchFragment = SearchFragment.getInstance();
    private LocalWordFragment localWordFragment =  LocalWordFragment.getInstance();
    public Fragment currentFragment = articleFragment;
    private EditText editText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        fragmentManager = getChildFragmentManager();
        initFragment();//初始化
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText = getActivity().findViewById(R.id.search_view_editText);
        Log.d(TAG, "onActivityCreated: " + editText.getText().toString());
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                WordDao wordDao = new WordDao();
                List<Word> words = wordDao.searchWord(editable.toString());
                EventBus.getDefault().post(new MessageEvent(words));
                if (currentFragment != searchFragment) {
                    switchFragment(currentFragment, searchFragment);
                } else {
                    if (fragmentManager.getBackStackEntryCount() == 0) {    //进入模糊搜索又返回的情况
                        currentFragment = articleFragment;
                        switchFragment(currentFragment, searchFragment);
                    }
                    searchFragment.onHiddenChanged(false);
                }
            }
        };
        editText.addTextChangedListener(watcher);

        ImageButton button_search = getActivity().findViewById(R.id.search_view_button);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null)
                    manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                String word = editText.getText().toString().trim();
                Log.d(TAG, "onClick: " + word + "( 1 )");
                if (!word.equals("")) {
                    search(word);
                }
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

    /**
     * 初始化ArticleFragment 和 WordInfoFragment, 并且隐藏 WordInfoFragment, 当WordInfoFragment显示/隐藏的时候，会调用onHiddenChanged
     */
    private void initFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.home_fragment_container, articleFragment, articleFragment.getClass().getName())
                .add(R.id.home_fragment_container, wordInfoFragment, wordInfoFragment.getClass().getName())
                .add(R.id.home_fragment_container, searchFragment, searchFragment.getClass().getName())
                .add(R.id.home_fragment_container,localWordFragment, localWordFragment.getClass().getName())
                .hide(localWordFragment)
                .hide(searchFragment)
                .hide(wordInfoFragment);
        fragmentTransaction.commit(); //一定要记得commit！
    }

    /**
     * 切换fragment 如果articleFragment显示，则隐藏，显示wordFragment；如果articleFragment隐藏则需要再次调用wordFragment的onHiddenChanged
     * 方法更新数据。
     */
    private void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            currentFragment = articleFragment;
            from = articleFragment;
        }
        Log.d(TAG, "switchFragment: from " + from  + "\n to " + to);
        if (from != to) {
            fragmentTransaction
                    .hide(from)
                    .show(to);
            if (to == searchFragment) {
                fragmentTransaction
                        .addToBackStack("toSearch")
                        .commit();
            } else if (to == wordInfoFragment) {
                fragmentTransaction
                        .addToBackStack("toWordInfo")
                        .commit();
            }
        }
        currentFragment = to;
    }

    private void sentHttpRequest(String url) {
        Log.d(TAG, "sentHttpRequest: begin");
        HttpUtil.setHttpRequest(url, new HttpCallBackListener() {
            @Override
            public void onFinish(InputStream inputStream) {
                WordHandler wordHandler = new WordHandler();
                ParseXML.parse(wordHandler, inputStream);
                Log.d(TAG, "onFinish: callback");
                callBack(wordHandler.getWordDto());
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void callBack(WordDto wordDto) {
        this.wordDto = wordDto;
        countDownLatch.countDown();
    }

    private String getUrl(String word) {
        String url = "https://dict-co.iciba.com/api/dictionary.php?key=7543E04062DE5B2E5370A0627D59F529&w=";
        return url + word;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    public void search(String word) {
        try {
            countDownLatch = new CountDownLatch(1);
            sentHttpRequest(getUrl(word));
            countDownLatch.await();
            EventBus.getDefault().postSticky(new MessageEvent(wordDto));//发送事件
            editText.setText("");
            InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
            if (manager != null)
                manager.hideSoftInputFromWindow(getView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            switchFragment(currentFragment, wordInfoFragment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
