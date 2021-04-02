package com.example.bt5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class FragmentRed extends Fragment {
    MainActivity main;
    TextView maso, hoten, lop, dtb;
    Button next, previous, top, bottom;
    public static FragmentRed newInstance(String arg1){
        FragmentRed fragmentRed = new FragmentRed();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", arg1);
        fragmentRed.setArguments(bundle);
        return fragmentRed;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout view_layout = (ConstraintLayout) inflater.inflate(R.layout.inform_view, null);
        maso = (TextView) view_layout.findViewById(R.id.masoTxt);
        hoten = (TextView) view_layout.findViewById(R.id.nameTxt);
        lop = (TextView) view_layout.findViewById(R.id.lopTxt);
        dtb = (TextView) view_layout.findViewById(R.id.scoreTxt);
        int position = 0;
        int max = Integer.MAX_VALUE;
        try {
            Bundle arguments = this.getArguments();
            maso.setText(arguments.getString("id", "Maso"));
            hoten.setText("Ho ten: " + arguments.getString("name", ""));
            lop.setText("Lop: " + arguments.getString("course", ""));
            dtb.setText("Diem trung binh: " + arguments.getString("average", ""));
            position = Integer.parseInt(arguments.getString("position"));
            max = Integer.parseInt(arguments.getString("max"));
            /*arguments.remove("id");
            arguments.remove("name");
            arguments.remove("course");
            arguments.remove("average");
            arguments.remove("position");
            arguments.remove("max");*/
        } catch (Exception e){
            Log.e("Red error" , e.getMessage());
        }
        next = (Button) view_layout.findViewById(R.id.nextBtn);
        previous = (Button) view_layout.findViewById(R.id.prevBtn);
        top = (Button) view_layout.findViewById(R.id.firstBtn);
        bottom = (Button) view_layout.findViewById(R.id.lastBtn);
        int finalPosition = position;
        int finalMax = max;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (finalPosition < finalMax - 1) {
                    bundle.putString("activated", String.valueOf(finalPosition + 1));
                }
                else {
                    bundle.putString("activated", String.valueOf(finalMax - 1));
                }
                FragmentBlue fragmentBlue = new FragmentBlue();
                fragmentBlue.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.ListView, fragmentBlue).commit();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (finalPosition - 1 >= 0) {
                    bundle.putString("activated", String.valueOf(finalPosition - 1));
                }
                else {
                    bundle.putString("activated", String.valueOf(0));
                }
                FragmentBlue fragmentBlue = new FragmentBlue();
                fragmentBlue.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.ListView, fragmentBlue).commit();
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("activated", String.valueOf(0));
                FragmentBlue fragmentBlue = new FragmentBlue();
                fragmentBlue.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.ListView, fragmentBlue).commit();
            }
        });

        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("activated", String.valueOf(finalMax - 1));
                FragmentBlue fragmentBlue = new FragmentBlue();
                fragmentBlue.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.ListView, fragmentBlue).commit();
            }
        });
        return  view_layout;
    }
}
