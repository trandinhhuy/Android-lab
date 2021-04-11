package com.example.everhope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.everhope.FragmentComment.CommentAction;
import com.example.everhope.FragmentComment.CommentView;

public class CommentActivity extends FragmentActivity {
    FragmentTransaction ft;
    CommentAction commentAction;
    CommentView commentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar commentToolbar = findViewById(R.id.toolbar_comment);
        commentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ft = getSupportFragmentManager().beginTransaction();
        commentAction = CommentAction.newInstance();
        commentView = CommentView.newInstance();
        ft.replace(R.id.comment_action, commentAction);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.comment_view, commentView);
        ft.commit();
    }
}