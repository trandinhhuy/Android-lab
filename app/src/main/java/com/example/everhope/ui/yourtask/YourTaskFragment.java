package com.example.everhope.ui.yourtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.R;

public class YourTaskFragment extends Fragment {

    private YourTaskViewModel yourTaskViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourTaskViewModel =
                new ViewModelProvider(this).get(YourTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourtask, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        yourTaskViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}