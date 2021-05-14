package com.example.everhope;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AdminUserUnban extends AppCompatActivity {

    ArrayList<UnbannedObj> lst = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_tool_rp_main);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("User");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()){
                    UnbannedObj addNew = null;
                    String check = String.valueOf(item.child("Ban").getValue().toString());
                    if (check.compareTo("1")==0){
                        addNew = new UnbannedObj();
                        addNew.name = String.valueOf(item.child("Name").getValue());
                        addNew.email = String.valueOf(item.child("Email").getValue());
                        addNew.id = String.valueOf(item.getKey());
                    }
                    if (addNew!=null) {
                        lst.add(addNew);
                    }
                }

                if (lst.size() > 0) {
                    String[] title = new String[lst.size()];
                    for (int i = 0; i < lst.size(); i++) {
                        title[i] = lst.get(i).id;
                    }

                    AdminUnbanAdapter adapter = new AdminUnbanAdapter(AdminUserUnban.this,lst,title,2);
                    listView = (ListView)findViewById(R.id.admin_rp_list);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}