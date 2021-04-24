package com.example.myapplicationadmin.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplicationadmin.R;

public class ReportedItemAdapter extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] rp_datetime;
    private final String[] rp_reason;
    private final String[] rp_detail;
    private final int[] id;
    private final int rp_type;

    public ReportedItemAdapter(Activity context, String[] rp_datetime, String[] rp_reason, String[] rp_detail, int[] id, int type) {
        super(context, R.layout.rp_item, rp_reason);
        this.context=context;
        this.rp_reason=rp_reason;
        this.rp_datetime=rp_datetime;
        this.rp_detail=rp_detail;
        this.id = id;
        this.rp_type = type;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View v= inflater.inflate(R.layout.rp_item, null,true);

        TextView rpReason = (TextView) v.findViewById(R.id.txt_rpReason);
        TextView rpDatetime = (TextView) v.findViewById(R.id.txt_rpDatetime);
        TextView rpDetail = (TextView) v.findViewById(R.id.txt_rpDetail);

        rpReason.setText(rp_reason[position]);
        rpDatetime.setText(rp_datetime[position]);
        rpDetail.setText(rp_detail[position]);

        TextView btnShowInfo = (TextView)v.findViewById(R.id.btn_showInfo);
        TextView btnDelete = (TextView)v.findViewById(R.id.btn_delete);
        TextView btnBan = (TextView)v.findViewById(R.id.btn_ban);

        btnBan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure to ban this?");
                alertDialogBuilder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertDialogBuilder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        btnBan.setText("UNBANNNN");
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure to delete this request?");
                alertDialogBuilder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertDialogBuilder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnShowInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (rp_type==1) showEventInfo(position);
                else showUserInfo(position);
            }
        });

        return v;
    };

    public void showEventInfo(int position) {
        androidx.appcompat.app.AlertDialog.Builder alert;
        alert = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.show_info_event, null);

        TextView eID = (TextView) view.findViewById(R.id.rp_eventID);
        TextView eName = (TextView) view.findViewById(R.id.rp_eventName);
        TextView eDes = (TextView) view.findViewById(R.id.rp_eventDes);
        TextView eOrID = (TextView) view.findViewById(R.id.rp_EventHostID);
        TextView eContact = (TextView) view.findViewById(R.id.rp_eventContact);
        TextView eDatetime = (TextView) view.findViewById(R.id.rp_eventDatetime);
        TextView eLocation = (TextView) view.findViewById(R.id.rp_eventLocation);
        TextView eInterest = (TextView) view.findViewById(R.id.rp_eventInterest);
        TextView eClose = (TextView) view.findViewById(R.id.btn_closeRpInfo);

        // Lay thong tin tu database dua tren id[position] -> set text

        eID.setText("Load from database");
        eName.setText("Load from database");
        eDes.setText("Load from database");
        eInterest.setText("Load from database");
        eLocation.setText("Load from database");
        eContact.setText("Load from database");
        eDatetime.setText("Load from database");
        eOrID.setText("Load from database");


        alert.setView(view);
        alert.setCancelable(false);
        androidx.appcompat.app.AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        eClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showUserInfo(int position) {
        androidx.appcompat.app.AlertDialog.Builder alert;
        alert = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.show_info_user, null);

        TextView eID = (TextView) view.findViewById(R.id.rp_userID);
        TextView eName = (TextView) view.findViewById(R.id.rp_userName);
        TextView eDes = (TextView) view.findViewById(R.id.rp_userEmail);

        // Lay thong tin tu database dua tren id[position] -> set text

        eID.setText("Load from database");
        eName.setText("Load from database");
        eDes.setText("Load from database");
        TextView eClose = (TextView) view.findViewById(R.id.btn_closeRpInfo2);

        alert.setView(view);
        alert.setCancelable(false);
        androidx.appcompat.app.AlertDialog dialog = alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        eClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
