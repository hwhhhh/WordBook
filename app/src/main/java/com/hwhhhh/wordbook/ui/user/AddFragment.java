package com.hwhhhh.wordbook.ui.user;

import android.os.Bundle;
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

public class AddFragment extends Fragment {
    private static AddFragment addFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_word_add, container, false);
    }

    public static AddFragment getInstance() {
        if (addFragment == null) {
            synchronized (AddFragment.class) {
                if (addFragment == null) {
                    addFragment = new AddFragment();
                }
            }
        }
        return addFragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            final EditText editText_word = getView().findViewById(R.id.word_add);
            final EditText editText_pron = getView().findViewById(R.id.word_add_pronunciation);
            final EditText editText_acce = getView().findViewById(R.id.word_add_acceptation);
            Button button = getActivity().findViewById(R.id.word_add_button_save);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WordDao wordDao = WordDao.getInstance();
                    String word = editText_word.getText().toString().trim();
                    String pron = editText_pron.getText().toString().trim();
                    String acce = editText_acce.getText().toString().trim();
                    if (!word.equals("") && wordDao.add(word, pron, acce)) {
                        Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "添加失败！", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
