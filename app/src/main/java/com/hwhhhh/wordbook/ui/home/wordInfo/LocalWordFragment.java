package com.hwhhhh.wordbook.ui.home.wordInfo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.dao.WordDao;
import com.hwhhhh.wordbook.entity.Word;
import com.hwhhhh.wordbook.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LocalWordFragment extends Fragment {
    private static LocalWordFragment localWordFragment;
    private static final String TAG = "LocalWordFragment";
    private Word word;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return inflater.inflate(R.layout.fragment_word_local, container, false);
    }

    public static LocalWordFragment getInstance() {
        if (localWordFragment == null) {
            synchronized (LocalWordFragment.class) {
                if (localWordFragment == null) {
                    localWordFragment = new LocalWordFragment();
                }
            }
        }
        return localWordFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        this.word = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            Button button = getView().findViewById(R.id.word_local_button_save);
            final EditText editText_word = getView().findViewById(R.id.word_local);
            final EditText editText_pron = getView().findViewById(R.id.word_local_pronunciation);
            final EditText editText_acce = getView().findViewById(R.id.word_local_acceptation);
            if (word != null) {
                editText_word.setText(word.getWord());
                editText_pron.setText(word.getPronunciation());
                editText_acce.setText(word.getMeaning());

                editText_word.setFocusable(false);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String word = editText_word.getText().toString().trim();
                        String pron = editText_pron.getText().toString().trim();
                        String acce = editText_acce.getText().toString().trim();
                        WordDao wordDao = WordDao.getInstance();
                        if (wordDao.modify(word, pron, acce)) {
                            Toast.makeText(getActivity(), "修改成功！", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "修改失败！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWord(MessageEvent messageEvent) {
        if (messageEvent.getWords() != null) {
            if (messageEvent.getWords().size() > 0) {
                this.word = messageEvent.getWords().get(0);
                Log.d(TAG, "getWord: " + word.toString());
            } else {
                this.word = null;
            }
        }
    }
}
