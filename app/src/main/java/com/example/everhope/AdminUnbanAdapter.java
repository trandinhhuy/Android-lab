package com.example.everhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.everhope.supportClass.UpdateFirebase;

import java.util.ArrayList;

public class AdminUnbanAdapter extends ArrayAdapter<String> {
    private final Activity context;

    ArrayList<UnbannedObj> lst;
    int type;

    public AdminUnbanAdapter(Activity context, ArrayList<UnbannedObj> lst, String[] title, int type) {
        super(context, R.layout.admin_item_unban, title);
        this.context = context;
        this.lst = lst;
        this.type = type;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.admin_item_unban, null, true);

        TextView objID = (TextView) v.findViewById(R.id.admin_ban_item_ID);
        TextView objName = (TextView) v.findViewById(R.id.admin_ban_item_Name);
        TextView objEmail = (TextView) v.findViewById(R.id.admin_ban_item_email);

        objID.setText(lst.get(position).getId());
        objName.setText(lst.get(position).getName());
        objEmail.setText(lst.get(position).getEmail());


        TextView btnUnban = (TextView) v.findViewById(R.id.admin_ban_item_btn_ban);

        btnUnban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure to unban this?");
                alertDialogBuilder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertDialogBuilder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (type == 2) {
                            UpdateFirebase.updateData("User/" + lst.get(position).getId() + "/Ban", "0");
                            Intent intent = new Intent(context, AdminUserUnban.class);
                            context.startActivity(intent);
                            context.finish();
                        }
                        else {
                            UpdateFirebase.updateData("Event/" + lst.get(position).getId() + "/Ban", "0");
                            Intent intent = new Intent(context, AdminEventUnban.class);
                            context.startActivity(intent);
                            context.finish();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return v;
    };
}


