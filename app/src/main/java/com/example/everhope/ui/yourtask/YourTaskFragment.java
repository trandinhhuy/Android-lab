package com.example.everhope.ui.yourtask;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.AllEventActivity;
import com.example.everhope.R;

public class YourTaskFragment extends Fragment {

    private YourTaskViewModel yourTaskViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourTaskViewModel =
                new ViewModelProvider(this).get(YourTaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourtask, container, false);
        return root;
    }
}