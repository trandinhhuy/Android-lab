package com.example.everhope.FragmentComment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.everhope.R;
import com.example.everhope.customlist.CustomCommentList;

public class CommentView extends Fragment {
    Context context = null;
    Integer[] avatar = {R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person};
    String[] user_name = {"Tiger Tran", "Putin", "Persian"};
    String[] comment = {"t ban m a", "Interesting", "Where's my cat"};

    public static CommentView newInstance() {

        Bundle args = new Bundle();

        CommentView fragment = new CommentView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
        } catch(IllegalStateException e){

        }
        //db
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout commentView = (LinearLayout) inflater.inflate(R.layout.comment_view, null, true);
        ListView commentList = (ListView) commentView.findViewById(R.id.comment_list);
        //database
        CustomCommentList customCommentList = new CustomCommentList((Activity) context, avatar, user_name, comment);
        commentList.setAdapter(customCommentList);

        return commentView;
    }
}
