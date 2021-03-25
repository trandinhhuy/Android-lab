package com.example.test;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends ListActivity {

    GridView gridView;
    TextView txtSoloImage;
    ImageView soloImage;
    Button soloBack;

    private ListView listView;
    private String name_item[] = {
            "Nguyen Van A",
            "Le Thi B",
            "Tran Van C",
            "Phan Van C",
            "Dinh Van D"
    };

    private String phone_item[] = {
            "0989897873",
            "0967995843",
            "0907955843",
            "0967885811",
            "0988885231"
    };


    private Integer imageid[] = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting header

        ListView listView=(ListView)findViewById(android.R.id.list);

        // For populating list data
        CustomAdapterList customAdapterList = new CustomAdapterList(this, name_item, phone_item, imageid);
        listView.setAdapter(customAdapterList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView txtView = (TextView) findViewById(R.id.textView);
                txtView.setText("You choose: " + name_item[position]);
            }
        });
    }
}
