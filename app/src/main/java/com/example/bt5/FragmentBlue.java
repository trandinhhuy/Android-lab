package com.example.bt5;

import android.app.Activity;
import android.content.Context;
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

public class FragmentBlue extends Fragment {
    MainActivity main;
    Context context = null;
    String message = "";
    String [] maso = {
            "A1_9829", "A1_1809", "A2_3509", "A2_3100", "A1_1120"
    };
    Integer[] image_id = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5
    };
    Student[] inform = {
            new Student("Thai Quynh Mai", "A1", 10),
            new Student("Le thi A", "A1", 8),
            new Student("Tran Dinh Huy", "A1", 9),
            new Student("Thai Quynh Ni", "A1", 9),
            new Student("Vladimir Putin", "A0", 10)
    };
    public static FragmentBlue newInstance(String strArg){
        FragmentBlue fragment = new FragmentBlue();
        Bundle arg = new Bundle();
        arg.putString("strArg1", strArg);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e){
            throw new IllegalStateException("Main activity mus implements Callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout_left = (RelativeLayout) inflater.inflate(R.layout.list_view, null, true);
        TextView txtblue = (TextView) layout_left.findViewById(R.id.blueView);
        ListView listView = (ListView) layout_left.findViewById(R.id.listStudent);
        final TextView name = layout_left.findViewById(R.id.nameView);
        final ImageView avt = layout_left.findViewById(R.id.imageView);
        Bundle bundle = this.getArguments();
        int activated = Integer.parseInt(bundle.getString("activated", "-1"));
        CustomAdapterList adapterList = new CustomAdapterList((Activity) context, maso, image_id, activated);
        listView.setAdapter(adapterList);


        if (activated >= 0) {
            try {
                txtblue.setText(maso[activated]);
                Bundle putarg = new Bundle();
                putarg.putString("id", maso[activated]);
                putarg.putString("name", inform[activated].getName());
                putarg.putString("course", inform[activated].getCourse());
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
                Bundle bundle = new Bundle();
                bundle.putString("id", maso[position]);
                bundle.putString("name", inform[position].getName());
                bundle.putString("course", inform[position].getCourse());
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
