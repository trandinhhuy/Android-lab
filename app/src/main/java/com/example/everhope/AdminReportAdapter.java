package com.example.everhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.everhope.supportClass.UpdateFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminReportAdapter extends ArrayAdapter<String> {
    private final Activity context;

    ArrayList<ReportedObject> lst;
    private final int accountType;

    public AdminReportAdapter(Activity context, int type, ArrayList<ReportedObject> lst, String [] title) {
        super(context, R.layout.admin_rp_item, title);
        this.context = context;
        this.accountType = type;
        this.lst = lst;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View v= inflater.inflate(R.layout.admin_rp_item, null,true);

        TextView rpReason = (TextView) v.findViewById(R.id.admin_item_txt_rpReason);
        TextView rpDatetime = (TextView) v.findViewById(R.id.admin_item_txt_rpDatetime);
        TextView rpDetail = (TextView) v.findViewById(R.id.admin_item_txt_rpDetail);

        rpReason.setText(lst.get(position).getReason());
        rpDatetime.setText(lst.get(position).getDate() + "  " + lst.get(position).getTime());
        rpDetail.setText(lst.get(position).getDetail());

        TextView btnShowInfo = (TextView) v.findViewById(R.id.admin_item_btn_showInfo);
        TextView btnDelete = (TextView) v.findViewById(R.id.admin_item_btn_delete);
        TextView btnBan = (TextView) v.findViewById(R.id.admin_item_btn_ban);

        btnBan.setOnClickListener(new View.OnClickListener() {
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
                        if (accountType == 2) {
                            UpdateFirebase.updateData("User/" + lst.get(position).getReported() + "/Ban", "1");
                            UpdateFirebase.removeData("UserReport/" + lst.get(position).getKey() + "/");
                        } else { // if (accountType == 1)
                            UpdateFirebase.updateData("Event/" + lst.get(position).getReported() + "/Ban", "1");
                            UpdateFirebase.removeData("EventReport/" + lst.get(position).getKey() + "/");
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
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


        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountType == 1) showEventInfo(position);
                else showUserInfo(position);
            }
        });

        return v;
    };

    public void showEventInfo(int position) {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.admin_show_info_event, null);

        TextView eID = (TextView) view.findViewById(R.id.admin_event_rp_eventID);
        TextView eName = (TextView) view.findViewById(R.id.admin_event_rp_eventName);
        TextView eDes = (TextView) view.findViewById(R.id.admin_event_rp_eventDes);
        TextView eOrID = (TextView) view.findViewById(R.id.admin_event_rp_EventHostID);
        TextView eContact = (TextView) view.findViewById(R.id.admin_event_rp_eventContact);
        TextView eDatetime = (TextView) view.findViewById(R.id.admin_event_rp_eventDatetime);
        TextView eLocation = (TextView) view.findViewById(R.id.admin_event_rp_eventLocation);
        TextView eInterest = (TextView) view.findViewById(R.id.admin_event_rp_eventInterest);
        TextView eClose = (TextView) view.findViewById(R.id.admn_event_btn_closeRpInfo);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Event/" + lst.get(position).reported);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eID.setText(lst.get(position).reported);
                eName.setText(String.valueOf(snapshot.child("Name").getValue()));
                eDes.setText(String.valueOf(snapshot.child("Description").getValue()));
                eInterest.setText(String.valueOf(snapshot.child("Interest").getValue()));
                eLocation.setText(String.valueOf(snapshot.child("Location").getValue()));
                eContact.setText(String.valueOf(snapshot.child("Interest").getValue()));
                eDatetime.setText(String.valueOf(snapshot.child("Datetime").getValue()));
                eOrID.setText(String.valueOf(snapshot.child("Organizer").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        alert.setView(view);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
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
        View view = inflater.inflate(R.layout.admin_show_info_user, null);

        TextView eID = (TextView) view.findViewById(R.id.admin_user_rp_userID);
        TextView eName = (TextView) view.findViewById(R.id.admin_user_rp_userName);
        TextView eDes = (TextView) view.findViewById(R.id.admin_user_rp_userEmail);
        TextView eClose = (TextView) view.findViewById(R.id.btn_closeRpInfo2) ;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("User/" + lst.get(position).reported);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eID.setText(lst.get(position).reported);
                eName.setText(String.valueOf(snapshot.child("Name").getValue()));
                eDes.setText(String.valueOf(snapshot.child("Email").getValue()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
