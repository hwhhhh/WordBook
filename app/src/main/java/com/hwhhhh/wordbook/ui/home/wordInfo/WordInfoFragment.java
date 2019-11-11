package com.hwhhhh.wordbook.ui.home.wordInfo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.adapter.WordORIGAdapter;
import com.hwhhhh.wordbook.adapter.WordPosAdapter;
import com.hwhhhh.wordbook.dao.WordDao;
import com.hwhhhh.wordbook.dto.WordDto;
import com.hwhhhh.wordbook.entity.Word;
import com.hwhhhh.wordbook.entity.WordORIG;
import com.hwhhhh.wordbook.entity.WordPartOfSpeech;
import com.hwhhhh.wordbook.ui.home.HomeFragment;
import com.hwhhhh.wordbook.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WordInfoFragment extends Fragment {
    private static WordInfoFragment wordInfoFragment;
    private static final String TAG = "WordInfoFragment";

    private WordDto wordDto;
    private String wordStr;
    private MediaPlayer myMediaPlayerENG, myMediaPlayerUS;
    private TextToSpeech textToSpeech;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return inflater.inflate(R.layout.fragment_word_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ImageButton imageButton = getView().findViewById(R.id.word_info_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordDao wordDao = WordDao.getInstance();
                if (wordDao.isSaved(wordDto)) {
                    wordDao.delete(wordDto);
                    imageButton.setImageResource(R.drawable.ic_star_hollow);
                } else {
                    wordDao.saveWord(wordDto);
                    imageButton.setImageResource(R.drawable.ic_star_solid);
                }
            }
        });
        Log.d(TAG, "onActivityCreated: ");

        ImageButton deleteButton = getView().findViewById(R.id.word_info_button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordDao wordDao = WordDao.getInstance();
                List<Word> words = wordDao.searchWord(wordStr);
                if (wordDao.delete(words.get(0)) > 0) {
                    Toast.makeText(getActivity(), "成功从本地词库删除！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "此单词已从本地词库删除！", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageButton modifyButton = getView().findViewById(R.id.word_info_button_modify);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordDao wordDao = WordDao.getInstance();
                List<Word> words = wordDao.searchWord(wordStr);
                Log.d(TAG, "onClick: " + words.get(0).toString());
                EventBus.getDefault().post(new MessageEvent(words));

                FragmentTransaction fragmentTransaction = HomeFragment.getInstance().getChildFragmentManager().beginTransaction();
                fragmentTransaction
                        .hide(wordInfoFragment)
                        .show(LocalWordFragment.getInstance())
                        .addToBackStack("toLocalWord")
                        .commit();

                HomeFragment.getInstance().currentFragment = LocalWordFragment.getInstance();
            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
        myMediaPlayerENG.release();
        myMediaPlayerUS.release();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        WordDao wordDao = WordDao.getInstance();
        ImageButton imageButton = getView().findViewById(R.id.word_info_imageButton);

        if (!hidden) {
            if (wordDto != null) {
                if (wordDao.isSaved(wordDto)) {
                    Log.d(TAG, "onHiddenChanged:1  " + wordDto.getWordInfo().getKey());
                    imageButton.setImageResource(R.drawable.ic_star_solid);
                } else {
                    imageButton.setImageResource(R.drawable.ic_star_hollow);
                }
                ScrollView scrollView = getView().findViewById(R.id.word_info_scrollview);
                scrollView.smoothScrollTo(0, 0 );
                TextView word = getView().findViewById(R.id.item_word);
                TextView word_eng = getView().findViewById(R.id.item_word_pron_eng);
                TextView word_us = getView().findViewById(R.id.item_word_pron_us);

                speakChina();
                myMediaPlayerENG = new MediaPlayer();
                myMediaPlayerENG.setLooping(false);
                myMediaPlayerUS = new MediaPlayer();
                myMediaPlayerUS.setLooping(false);
                try {
                    if (wordDto.getWordInfo().getPronPsENG() != null) {
                        myMediaPlayerENG.setDataSource(wordDto.getWordInfo().getPronPsENG());
                        myMediaPlayerENG.prepare();
                    }
                    if (wordDto.getWordInfo().getPronPsUS() != null) {
                        myMediaPlayerUS.setDataSource(wordDto.getWordInfo().getPronPsUS());
                        myMediaPlayerUS.prepare();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                word.setText(wordDto.getWordInfo().getKey());
                String eng = " 英:/" + wordDto.getWordInfo().getPsENG() + "/";
                word_eng.setText(eng);
                String us = " 美:/" + wordDto.getWordInfo().getPsUS() + "/";
                word_us.setText(us);
                Log.d(TAG, "onHiddenChanged: " + wordDto.getWordInfo().getPronPsENG());
                //词性与释义
                ListView listViewPos = getView().findViewById(R.id.listView_word_pos);
                List<WordPartOfSpeech> wordPartOfSpeeches = wordDto.getWordPartOfSpeeches();
                listViewPos.setAdapter(new WordPosAdapter(getActivity(), wordPartOfSpeeches));
                //双语例句
                ListView listViewOrig = getView().findViewById(R.id.listView_word_orig);
                final List<WordORIG> wordORIGS = wordDto.getWordORIGs();
                listViewOrig.setAdapter(new WordORIGAdapter(getActivity(), wordORIGS));
                listViewOrig.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        textToSpeech.speak(wordORIGS.get(i).getOrig(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                });

                word_eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myMediaPlayerENG.start();
                    }
                });

                word_us.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myMediaPlayerUS.start();
                    }
                });
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void showWordDto (MessageEvent messageEvent) {
        if (messageEvent.getWordDto() != null) {
            this.wordDto = messageEvent.getWordDto();
            this.wordStr = wordDto.getWordInfo().getKey();
        }
    }

    private void speakChina() {
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int supported = textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }
}
