package com.example.everhope.FragmentComment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.MenuActivity;
import com.example.everhope.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommentAction extends Fragment {
    Context context = null;

    public static CommentAction newInstance() {

        Bundle args = new Bundle();

        CommentAction fragment = new CommentAction();
        fragment.setArguments(args);
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
        LinearLayout commentAction = (LinearLayout)inflater.inflate(R.layout.comment_action, null, true);
        ImageView sendButton = (ImageView) commentAction.findViewById(R.id.send_button);
        EditText comment = (EditText) commentAction.findViewById(R.id.your_comment);
        Bundle bundle = this.getActivity().getIntent().getExtras();
        String eventID = bundle.getString("EventID", "");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getText().toString().compareTo("")!=0){
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    String ID = MenuActivity.getMyLoginPref(getActivity());
                    String [] datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()).split(" ");
                    String date = datetime[0];
                    String time = datetime[1];
                    String uuid =  UUID.randomUUID().toString().replace("-", "");

                    String path = "EventComment/Event"+eventID+"/"+uuid;
                    firebaseDatabase.getReference(path+"/Content").setValue(comment.getText().toString());
                    firebaseDatabase.getReference(path+"/Date").setValue(date);
                    firebaseDatabase.getReference(path+"/Time").setValue(time);
                    firebaseDatabase.getReference(path+"/User").setValue(ID);


                    CommentView commentView = CommentView.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.comment_view,commentView).commit();
                    comment.setText("");
                }

            }


        });
        return commentAction;
    }
}
