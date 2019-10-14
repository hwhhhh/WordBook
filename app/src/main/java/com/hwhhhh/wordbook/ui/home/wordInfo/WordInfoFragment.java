package com.hwhhhh.wordbook.ui.home.wordInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.adapter.WordORIGAdapter;
import com.hwhhhh.wordbook.adapter.WordPosAdapter;
import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;

import java.util.ArrayList;
import java.util.List;

public class WordInfoFragment extends Fragment {
    private static WordInfoFragment wordInfoFragment;
    private static final String TAG = "WordInfoFragment";

    private ListView listViewPos, listViewOrig;
    private WordDto wordDto;
    private List<WordPartOfSpeech> wordPartOfSpeeches = new ArrayList<>();
    private List<WordORIG> wordORIGS = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_info, container, false);
        Log.d(TAG, "onCreateView: WordInfoFragment");
        Bundle bundle = getArguments();
        wordDto = (WordDto) bundle.getSerializable("data");
        Log.d(TAG, "onCreateView: " + wordDto.getWordInfo().toString());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: WordInfoFragment");

    }

    public static WordInfoFragment getInstance() {
        if (wordInfoFragment == null) {
            synchronized(WordInfoFragment.class) {
                if (wordInfoFragment == null) {
                    wordInfoFragment = new WordInfoFragment();
                }
            }
        }
        return wordInfoFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (wordDto != null) {
            TextView word = getActivity().findViewById(R.id.item_word);
            TextView word_eng = getActivity().findViewById(R.id.item_word_pron_eng);
            TextView word_us = getActivity().findViewById(R.id.item_word_pron_us);
            word.setText(wordDto.getWordInfo().getKey());
            word_eng.setText(wordDto.getWordInfo().getPsENG());
            word_us.setText(wordDto.getWordInfo().getPsUS());

            //词性与释义
            listViewPos = getActivity().findViewById(R.id.listView_word_pos);
            wordPartOfSpeeches = wordDto.getWordPartOfSpeeches();
            listViewPos.setAdapter(new WordPosAdapter(getActivity(), wordPartOfSpeeches));
            //双语例句
            listViewOrig = getActivity().findViewById(R.id.listView_word_orig);
            wordORIGS = wordDto.getWordORIGs();
            listViewOrig.setAdapter(new WordORIGAdapter(getActivity(), wordORIGS));
        }
    }

}
