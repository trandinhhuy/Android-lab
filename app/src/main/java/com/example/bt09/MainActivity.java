package com.example.bt09;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    // Main GUI - A NEWS application based on National Public Radio RSS material
    ArrayAdapter<String> adapterMainSubjects;
    ListView myMainListView;
    Context context;
    SingleItem selectedNewsItem;
    // hard-coding main NEWS categories (TODO: use a resource file)
    String[][] myUrlCaptionMenu;
    //define convenient URL and CAPTIONs arrays


    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy",
                Locale.US);

        return sdf.format(new Date()); //Monday Apr 7, 2014
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name", "");
        if (name.isEmpty()){
            return;
        }
        if (name.contains("Thanh nien")){
            myUrlCaptionMenu = new String[][]{
                    {"https://thanhnien.vn/video/thoi-su.rss", "Thoi su"},
                    {"https://thanhnien.vn/video/phong-su.rss", "Phong su"},
                    {"https://thanhnien.vn/video/giai-tri.rss", "Giai tri"},
                    {"https://thanhnien.vn/video/mon-ngon.rss", "Mon ngon"},
                    {"https://thanhnien.vn/video/video-the-thao.rss", "The thao"}
            };
        }
        else if (name.contains("Tuoi tre")){
            myUrlCaptionMenu = new String[][]{
                    {"https://tuoitre.vn/rss/phap-luat.rss", "Phap Luat"},
                    {"https://tuoitre.vn/rss/nhip-song-so.rss", "Cong Nghe"},
                    {"https://tuoitre.vn/rss/nhip-song-tre.rss", "Nhip song tre"},
                    {"https://tuoitre.vn/rss/giai-tri.rss", "Giai tri"},
                    {"https://tuoitre.vn/rss/giao-duc.rss", "Giao duc"}
            };
        }
        else if (name.contains("Vietnamnet")){
            myUrlCaptionMenu = new String[][]{
                    {"https://vietnamnet.vn/rss/thoi-su-chinh-tri.rss", "Chinh tri"},
                    {"https://vietnamnet.vn/rss/thoi-su.rss", "Thoi su"},
                    {"https://vietnamnet.vn/rss/kinh-doanh.rss", "Kinh doanh"},
                    {"https://vietnamnet.vn/rss/giai-tri.rss", "Giai tri"},
                    {"https://vietnamnet.vn/rss/giao-duc.rss", "Giao duc"}
            };
        }
        else if (name.contains("Cafebiz")){
            myUrlCaptionMenu = new String[][]{
                    {"https://cafebiz.vn/phap-luat.rss", "Phap luat"},
                    {"https://cafebiz.vn/thoi-su.rss", "Thoi su"},
                    {"https://cafebiz.vn/cau-chuyen-kinh-doanh.rss", "Kinh doanh"},
                    {"https://cafebiz.vn/cong-nghe.rss", "Cong nghe"},
                    {"https://cafebiz.vn/giao-duc.rss", "Giao duc"}
            };
        }

        String[] myUrlCaption = new String[myUrlCaptionMenu.length];
        String[] myUrlAddress = new String[myUrlCaptionMenu.length];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView channelIn = (TextView) findViewById(R.id.channel_in);
        channelIn.setText("Channel in " + name);
        for (int i = 0; i < myUrlAddress.length; i++) {
            myUrlAddress[i] = myUrlCaptionMenu[i][0];
            myUrlCaption[i] = myUrlCaptionMenu[i][1];
        }
        context = getApplicationContext();
        this.setTitle("NPR Headline News\n" + niceDate());
        // user will tap on a ListView’s row to request category’s headlines
        myMainListView = (ListView) this.findViewById(R.id.myListView);
        myMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = myUrlAddress[_index], urlCaption = myUrlCaption[_index];
                //create an Intent to talk to activity: ShowHeadlines
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadlines.class);
                //prepare a Bundle and add the input arguments: url & caption
                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress);
                myData.putString("items", myUrlCaption[_index]);
                myData.putString("channelss", name);
                myData.putString("urlCaption", urlCaption);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });
        // fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myUrlCaption);
        myMainListView.setAdapter(adapterMainSubjects);
    }
}