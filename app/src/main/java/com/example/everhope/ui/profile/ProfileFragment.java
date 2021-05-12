package com.example.everhope.ui.profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.everhope.MenuActivity;
import com.example.everhope.R;
import com.example.everhope.userProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ProfileFragment extends Fragment {
    public SharedPreferences pref;
    String curPass = "abc123";
    Button btn_update, btn_changepass, btn_updatepass;
    ImageButton btn_edit;
    EditText edit_name, edit_des, edit_dob, edit_interests, edit_currentpass, edit_newpass, edit_confirmpass, edit_gender, edit_phone;
    TextView username, dob, des, interests, events, gender, phone;
    Calendar c;
    DatePickerDialog dpd;
    FloatingActionButton btn_exit,btn_exitpass;
    int checkedItem=0;
    String dt="";

    ImageView avatar;
    
    Uri imageUri;
    private ProfileViewModel profileViewModel;
    Activity activity;
    public static ProfileFragment newInstance(SharedPreferences pref, Activity host) {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.pref = pref;
        fragment.activity = host;
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        avatar = (ImageView) root.findViewById(R.id.avatar);
        ImageView chooseAvt = (ImageView) root.findViewById(R.id.selectAvatar);

        username = (TextView)root.findViewById(R.id.username);
        dob = (TextView)root.findViewById(R.id.dob);
        interests = (TextView)root.findViewById(R.id.interests);
        des = (TextView)root.findViewById(R.id.des);
        gender = (TextView) root.findViewById(R.id.gender);
        phone = (TextView) root.findViewById(R.id.phone);
        events = (TextView)root.findViewById(R.id.events);
        chooseAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });
        btn_edit = (ImageButton) root.findViewById(R.id.btn_edit);
        String userID = pref.getString("userID", "-1");

        if (userID.compareTo("-1") == 0){
            return root;
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference().child("User").child(userID);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(String.valueOf(snapshot.child("Name").getValue()));
                dob.setText(String.valueOf(snapshot.child("Dob").getValue()));
                gender.setText(String.valueOf(snapshot.child("Gender").getValue()));
                phone.setText(String.valueOf(snapshot.child("Phone").getValue()));
                des.setText(String.valueOf(snapshot.child("Description").getValue()));
                interests.setText(String.valueOf(snapshot.child("Interest").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref = storageReference.child("Avatar/User" + userID + "/");
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    try {
                        final File localFile = File.createTempFile("avatar", "jpg");
                        item.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                ImageView avatar = (ImageView) root.findViewById(R.id.avatar);
                                avatar.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                showDialog();
            }
        });

        return root;
    }

    private void selectImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null){
            imageUri = data.getData();
            avatar.setImageURI(imageUri);
            uploadImage(imageUri);
        }
    }

    private String getFileExtension(Uri imageUri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(imageUri));
    }
    private void uploadImage(Uri imagesUri){

        //remove current file
        FirebaseStorage removeFirebase = FirebaseStorage.getInstance();
        StorageReference removeRef = removeFirebase.getReference().child("Avatar/User" + MenuActivity.getMyLoginPref(getActivity()));

        removeRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()){
                    item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }
        });


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference fileStorage = firebaseStorage.getReference().child("Avatar/User" + MenuActivity.getMyLoginPref(getActivity()) + "/" + String.valueOf(System.currentTimeMillis() + "." + getFileExtension(imageUri)));
        fileStorage.putFile(imagesUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Avatar has been uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText((Context) getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder alert;

        alert = new AlertDialog.Builder(this.getContext(),R.style.MaterialThemeDialog);


        LayoutInflater inflater = getLayoutInflater();

        View view =  inflater.inflate(R.layout.up_edit,null);

        edit_name = (EditText) view.findViewById(R.id.edit_name);
        edit_name.setText(username.getText().toString());
        edit_des = (EditText) view.findViewById(R.id.edit_des);
        edit_des.setText(des.getText().toString());
        edit_dob = (EditText) view.findViewById(R.id.edit_dob);
        edit_dob.setText(dob.getText().toString());
        edit_gender = (EditText) view.findViewById(R.id.edit_gender);
        edit_gender.setText(gender.getText().toString());
        edit_phone = (EditText) view.findViewById(R.id.edit_phone);
        edit_phone.setText(phone.getText().toString());
        edit_interests = (EditText) view.findViewById(R.id.edit_interests);
        edit_interests.setText(interests.getText().toString());
        btn_update = (Button) view.findViewById(R.id.btn_update);
        btn_exit = (FloatingActionButton) view.findViewById(R.id.btn_exit);
        btn_changepass = (Button) view.findViewById(R.id.btn_changepass);

        alert.setView(view);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText(edit_name.getText().toString());
                des.setText(edit_des.getText().toString());
                dob.setText(edit_dob.getText().toString());
                interests.setText(edit_interests.getText().toString());
                gender.setText(edit_gender.getText().toString());
                phone.setText(edit_phone.getText().toString());
                String ID = MenuActivity.getMyLoginPref(getActivity());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                String path = "User/"+ID;
                firebaseDatabase.getReference(path+"/Description").setValue(edit_des.getText().toString());
                firebaseDatabase.getReference(path+"/Dob").setValue(edit_dob.getText().toString());
                firebaseDatabase.getReference(path+"/Interest").setValue(edit_interests.getText().toString());
                firebaseDatabase.getReference(path+"/Gender").setValue(edit_gender.getText().toString());
                firebaseDatabase.getReference(path+"/Phone").setValue(edit_phone.getText().toString());
                firebaseDatabase.getReference(path+"/Name").setValue(edit_name.getText().toString());


                dialog.dismiss();

            }
        });

        edit_dob.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getActivity(), R.style.alert,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dt=(mDay<10?"0":"")+mDay + "/" + (mMonth<9?"0":"")+(mMonth + 1) + "/" + mYear;
                        edit_dob.setText(dt);
                    }
                }, day,month, year);
                dpd.show();
            }
        });

        edit_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.alert);
                alertDialog.setTitle("Choose Gender");
                String[] items = {"Male","Female","Others"};
                String g = edit_gender.getText().toString();
                if(g.compareTo("Male")==0){
                    checkedItem=0;
                }
                else if(g.compareTo("Female")==0){
                    checkedItem=1;
                }
                else checkedItem=2;
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem = which;
                    }
                });

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_gender.setText(items[checkedItem]);
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        edit_interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.alert);
                String[] interestsArray = new String[]{"Environment", "Children", "Culture", "Education", "Animals", "Homeless", "Health", "Broadcasting", "Sports", "People"};
                boolean[] checkedInterests = new boolean[]{false, false, false, false,false, false, false, false,false, false};

                String[] tempToken = edit_interests.getText().toString().split(", ");
                for (int idx = 0; idx < tempToken.length; idx++) {
                    for (int idy = 0; idy < interestsArray.length; idy++) {
                        if (tempToken[idx].compareTo(interestsArray[idy]) == 0) {
                            checkedInterests[idy] = true;
                            break;
                        }
                    }
                }

                final List<String> interestsList = Arrays.asList(interestsArray);
                builder.setTitle("Select this event's interests/fields");
                builder.setMultiChoiceItems(interestsArray, checkedInterests, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        checkedInterests[which] = isChecked;
                    }
                });

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String intst = "";
                        for (int i = 0; i < checkedInterests.length; i++) {
                            boolean checked = checkedInterests[i];
                            if (checked) {
                                intst += (intst.length()>0 ?", ":"")+interestsList.get(i);
                            }
                        }
                        edit_interests.setText(intst);
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });


        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showDialog1() {
        AlertDialog.Builder alert1;

        alert1 = new AlertDialog.Builder(getActivity(), R.style.MaterialThemeDialog);


        LayoutInflater inflater1 = getLayoutInflater();

        View view1 =  inflater1.inflate(R.layout.up_changepass,null);


        edit_currentpass = (EditText) view1.findViewById(R.id.edit_currentpass);
        edit_newpass = (EditText) view1.findViewById(R.id.edit_newpass);
        edit_confirmpass = (EditText) view1.findViewById(R.id.edit_confirmpass);
        btn_updatepass = (Button) view1.findViewById(R.id.btn_updatepass);
        btn_exitpass = (FloatingActionButton) view1.findViewById(R.id.btn_exitpass);

        alert1.setView(view1);
        alert1.setCancelable(false);

        AlertDialog dialog1 = alert1.create();
        dialog1.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog1.show();
        btn_updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curPass.equals(edit_currentpass.getText().toString()) && edit_newpass.getText().toString().equals(edit_confirmpass.getText().toString())){
                    Toast.makeText(getActivity(),"Change password successfully!",Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();

                }
                else {
                    Toast.makeText(getActivity(),"Error! Please re-enter",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_exitpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


    }

}