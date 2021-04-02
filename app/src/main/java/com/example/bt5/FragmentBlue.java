package com.example.bt5;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentBlue extends Fragment {
    MainActivity main;
    Context context = null;
    String message = "";
    static SQLiteDatabase db;
    String [] maso;
    Integer[] image_id;
    Student[] inform;
    public static FragmentBlue newInstance(String strArg, SQLiteDatabase db){
        FragmentBlue fragment = new FragmentBlue();
        Bundle arg = new Bundle();
        arg.putString("strArg1", strArg);
        fragment.setArguments(arg);
        fragment.db = db;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
        } catch (IllegalStateException e){

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Cursor count = db.rawQuery("Select count(*) from Student", null);
        if (count.moveToNext()){
            maso = new String[count.getInt(0)];
            image_id = new Integer[count.getInt(0)];
            inform = new Student[count.getInt(0)];
        }
        Cursor data = db.rawQuery("Select * from Student", null);
        data.moveToPosition(-1);
        int i = 0;
        while(data.moveToNext()){
            maso[i] = data.getString(0);
            image_id[i] = data.getInt(1);
            String name = data.getString(2);
            String course = data.getString(3);
            String average = data.getString(4);
            inform[i] = new Student(name, course, Double.parseDouble(average));
            i++;
        }

        RelativeLayout layout_left = (RelativeLayout) inflater.inflate(R.layout.list_view, null, true);
        TextView txtblue = (TextView) layout_left.findViewById(R.id.blueView);
        ListView listView = (ListView) layout_left.findViewById(R.id.listStudent);
        final TextView name = layout_left.findViewById(R.id.nameView);
        final ImageView avt = layout_left.findViewById(R.id.imageView);
        Bundle bundle = this.getArguments();
        int activated = Integer.parseInt(bundle.getString("activated", "-1"));
        CustomAdapterList adapterList = new CustomAdapterList((Activity) context,maso,image_id, activated);
        listView.setAdapter(adapterList);


        if (activated >= 0) {
            Cursor lop = db.rawQuery("Select Tenlop from Course where Malop = '" + inform[activated].getCourse() + "'", null);
            String tenlop = "";
            if (lop.moveToNext()){
                tenlop = lop.getString(0);
            }
            try {
                txtblue.setText(maso[activated]);
                Bundle putarg = new Bundle();
                putarg.putString("id", maso[activated]);
                putarg.putString("name", inform[activated].getName());
                putarg.putString("course", tenlop);
                putarg.putString("average", inform[activated].getAverage());
                putarg.putString("position", String.valueOf(activated));
                putarg.putString("max", String.valueOf(maso.length));
                txtblue.setText("Ma so: " + maso[activated]);
                FragmentRed fragmentRed = new FragmentRed();
                fragmentRed.setArguments(putarg);
                getFragmentManager().beginTransaction().replace(R.id.Information, fragmentRed).commit();
            } catch (Exception e){
                Log.e("error", e.getMessage());
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor lop = db.rawQuery("Select Tenlop from Course where Malop = '" + inform[position].getCourse() + "'", null);
                String tenlop = "";
                if (lop.moveToNext()){
                    tenlop = lop.getString(0);
                }
                Bundle bundle = new Bundle();
                bundle.putString("id", maso[position]);
                bundle.putString("name", inform[position].getName());
                bundle.putString("course", tenlop);
                bundle.putString("average", inform[position].getAverage());
                bundle.putString("position", String.valueOf(position));
                bundle.putString("max", String.valueOf(maso.length - 1));
                txtblue.setText("Ma so: " + maso[position]);
                for (int i = 0 ; i < listView.getChildCount() ; i++){
                    if (position == i){
                        listView.getChildAt(position).setBackgroundColor(R.color.light_red);
                    }
                    else{
                        listView.getChildAt(i).setBackground(null);
                    }
                }
                FragmentRed fragmentRed = new FragmentRed();
                fragmentRed.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.Information, fragmentRed).commit();
            }
        });

        return layout_left;
    }
}
