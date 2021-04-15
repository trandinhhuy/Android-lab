package com.example.bt09;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PaperActivity extends Activity {
    ArrayAdapter<String> adapter;
    String[] papers = {"Thanh nien", "Cafebiz", "Vietnamnet", "Tuoi tre"};
    ListView listPaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        listPaper = (ListView) findViewById(R.id.paper_list);
        listPaper.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_single_paper, papers));
        listPaper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PaperActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", papers[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}