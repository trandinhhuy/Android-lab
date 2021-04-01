package com.example.everhope.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.EventView;
import com.example.everhope.R;

public class HomeFragment extends Fragment {
    ViewGroup scrollView;
    ImageView icon;
    TextView caption;
    TextView day;
    String [] id = {"hd1", "hd2", "hd3"};
    String[] items = {"Nhat rac", "Don bien", "Trong cay"};
    int[] volunteerImages = {R.drawable.volun1, R.drawable.volun2, R.drawable.volun3};
    String[] date = {"25/3/2021", "24/3/2021", "23/3/2021"};
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        scrollView = (ViewGroup) root.findViewById(R.id.viewActivity);
        for (int i = 0 ; i < items.length ; i++){
            final View singleFrame = getLayoutInflater().inflate(R.layout.activity_view, null);
            singleFrame.setId(i);
            ImageView icon = (ImageView) singleFrame.findViewById(R.id.iconImg);
            TextView caption = (TextView) singleFrame.findViewById(R.id.caption);
            TextView dateView = (TextView) singleFrame.findViewById(R.id.date);

            icon.setImageResource(volunteerImages[i]);
            caption.setText(items[i]);
            dateView.setText(date[i]);

            scrollView.addView(singleFrame);
            singleFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EventView.class);
                    startActivity(intent);
                }
            });
        }
        return root;

    }
}