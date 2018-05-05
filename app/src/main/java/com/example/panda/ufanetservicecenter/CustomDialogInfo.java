package com.example.panda.ufanetservicecenter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

public class CustomDialogInfo extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Button ok;
    public TextView TVcause, TVdate, TVmaster;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    String MyRefText;
    String Date;

    public CustomDialogInfo(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_info);



        myRef = database.getReference("repairs/" + MyRefText);

        final String name = MyRefText;
        final String date = Date;

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getInfoUsers((Map<FirebaseClassRepairs, Object>) dataSnapshot.getValue(), name, date);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        TVcause = findViewById(R.id.textViewCause);
        TVdate = findViewById(R.id.textViewDate);
        TVmaster = findViewById(R.id.textViewMaster);


        ok = findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);

    }

    public void setMyRef(String text) {
        MyRefText = text;
    }

    public void setDate(String date) {
        Date = date;
    }

    private void getInfoUsers(Map<FirebaseClassRepairs, Object> mechanism, String name, String date) {
        if (mechanism != null) {
            for (Map.Entry<FirebaseClassRepairs, Object> entry : mechanism.entrySet()) {
                Map singleUser = (Map) entry.getValue();

                if (singleUser.containsValue(name) && singleUser.containsValue(date)) {
                    TVcause.setText(String.valueOf(singleUser.get("cause")));
                    TVdate.setText(String.valueOf(singleUser.get("date")));
                    TVmaster.setText(String.valueOf(singleUser.get("Mlastname")) + " " + String.valueOf(singleUser.get("Mname")));
                }
            }
        }
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

















