package com.hwhhhh.wordbook.ui.home.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.adapter.WordListAdapter;
import com.hwhhhh.wordbook.adapter.WordSearchAdapter;
import com.hwhhhh.wordbook.entity.Word;
import com.hwhhhh.wordbook.ui.home.HomeFragment;
import com.hwhhhh.wordbook.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SearchFragment extends Fragment {
    private static SearchFragment searchFragment;
    private static final String TAG = "SearchFragment";
    private List<Word> words;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public static SearchFragment getInstance() {
        if (searchFragment == null) {
            synchronized(SearchFragment.class) {
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                }
            }
        }
        return searchFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (this.words != null) {
                final ListView listView = getView().findViewById(R.id.search_listView_fuzzySearch);
                listView.setAdapter(new WordSearchAdapter(getActivity(), words));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HomeFragment.getInstance().search(words.get(i).getWord());
                    }
                });
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWord(MessageEvent messageEvent) {
        this.words = messageEvent.getWords();
    }
}
