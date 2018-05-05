package com.example.panda.ufanetservicecenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

public class CustomDialogAdd extends Dialog implements
        View.OnClickListener {

    private static final String TAG = "CustomDialogAdd";
    public Activity c;
    public Button add, cancel;
    public EditText EDaddDate;
    public EditText EDaddCause;
    public TextWatcher tw;
    public final FirebaseClassRepairs repairs = new FirebaseClassRepairs();
    public DatabaseReference myRef;
    public DatabaseReference myRefUser;
    public FirebaseDatabase database;
    public String MyRefText;

    public CustomDialogAdd(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_add);
        EDaddDate = findViewById(R.id.EDaddDate);
        tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s.%s.%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    EDaddDate.setText(current);
                    EDaddDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        EDaddDate.addTextChangedListener(tw);
        EDaddCause = findViewById(R.id.EDaddCause);
        add = findViewById(R.id.addRepairButton);
        cancel = findViewById(R.id.cancelRepairButton);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();




        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getContext().getSharedPreferences("sID", Context.MODE_PRIVATE);
        String ans1 = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));
        myRefUser = database.getReference("users/" + ans1);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelRepairButton:
                dismiss();
                break;
            case R.id.addRepairButton:
                writeNewMech();
                break;
            default:
                break;
        }
        dismiss();
    }


    private void writeNewMech() {
            myRefUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);

                    myRef = database.getReference("repairs/" + MyRefText + "/");
                    repairs.Mlastname = value.lastname;
                    repairs.Mname = value.name;
                    repairs.cause = String.valueOf(EDaddCause.getText());
                    repairs.date = String.valueOf(EDaddDate.getText());
                    repairs.QR = MyRefText;
                    String nameRepair = String.valueOf(EDaddDate.getText());
                    myRef.child(nameRepair.substring(0, 2) + nameRepair.substring(3, 5) + nameRepair.substring(8, 10)).setValue(repairs);
                    Log.d(TAG, "EZ EZ EZ");
                    Log.d(TAG, value.name);
                    Log.d(TAG, value.lastname);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Не удалось прочитать значение", error.toException());
                }
            });


        One listener1 = (One) c;
        listener1.onButtonSelected(9);
    }

    public interface One {
        void onButtonSelected(int fragIndex);
    }


    public void setMyRef(String text) {
        MyRefText = text;
    }

}

















