package com.hwhhhh.wordbook.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hwhhhh.wordbook.R;
import com.hwhhhh.wordbook.ui.home.HomeFragment;
import com.hwhhhh.wordbook.ui.home.wordInfo.LocalWordFragment;
import com.hwhhhh.wordbook.util.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class UserFragment extends Fragment {
    private static UserFragment userFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initAddFragment();
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public static UserFragment getInstance() {
        if (userFragment == null) {
            synchronized (UserFragment.class) {
                if (userFragment == null) {
                    userFragment = new UserFragment();
                }
            }
        }
        return userFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            Button button_save = getView().findViewById(R.id.user_word_add);
            button_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction
                            .show(AddFragment.getInstance())
                            .commit();
                }
            });
        }
    }

    public void initAddFragment() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.user_host_fragment, AddFragment.getInstance(), "AddFragment")
                .hide(AddFragment.getInstance())
                .commit();
    }
}
