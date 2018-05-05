package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Map;

public class FragmentRegisterMechanism extends Fragment {
    private static final String TAG = "FragmentRegisterMecha";
    final FirebaseClassMechanism mech = new FirebaseClassMechanism();
    final FirebaseClassRepairs repairs = new FirebaseClassRepairs();
    private FirebaseAuth mAuth;
    EditText edName;
    EditText edDate;
    EditText edFirm;
    EditText edKind;
    EditText edAddress;
    EditText edCause;
    EditText edQR;
    DatabaseReference myRef;
    DatabaseReference myRef1;
    DatabaseReference myRef2;
    String AuthenticationID;
    String QRcode;
    String masterLastname;
    String masterName;
    boolean check;
    FloatingActionButton fab;
    FirebaseDatabase database;
    Button ButtonHistory;

    android.support.design.widget.TextInputLayout TVLdate;
    android.support.design.widget.TextInputLayout TVLcause;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_mechanism, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Информация о технике");

        edQR = rootView.findViewById(R.id.EDQR);
        edKind = rootView.findViewById(R.id.EDkind);
        edDate = rootView.findViewById(R.id.EDdate);
        TVLdate = rootView.findViewById(R.id.DATE);
        TVLcause = rootView.findViewById(R.id.CAUSE);
        ButtonHistory = rootView.findViewById(R.id.history);

        TVLdate.setVisibility(View.VISIBLE);
        TVLcause.setVisibility(View.VISIBLE);
        ButtonHistory.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            QRcode = bundle.getString("QRcode");
            edQR.setText(QRcode);
            check = bundle.getBoolean("booleanCheckDB");
        }


        ButtonHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                One listener1 = (One) getActivity();
                listener1.onButtonSelected(9);
            }
        });

        TextWatcher tw = new TextWatcher() {
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
                    edDate.setText(current);
                    edDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edDate.addTextChangedListener(tw);
        edFirm = rootView.findViewById(R.id.EDfirm);
        edName = rootView.findViewById(R.id.EDname);
        edAddress = rootView.findViewById(R.id.EDaddress);
        edCause = rootView.findViewById(R.id.EDcause);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef1 = database.getReference();
        myRef2 = database.getReference();

        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        String ans1 = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));

        myRef = database.getReference("users/" + ans1);
        myRef1 = database.getReference("mech/");
        myRef2 = database.getReference("repairs/");
        AuthenticationID = ans1;
        getLastName();
        if (check) {
            checkDB();
        }

        fab = rootView.findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((edQR.getText().length() != 0) &&
                        (edAddress.getText().length() != 0) &&
                        (edName.getText().length() != 0) &&
                        (edCause.getText().length() != 0) &&
                        (edDate.getText().length() != 0) &&
                        (edFirm.getText().length() != 0) &&
                        (edKind.getText().length() != 0)) {
                    Snackbar.make(view, "Информация о технике успешна добавлена", Snackbar.LENGTH_LONG).show();
                    writeNewMech();
                } else {
                    Snackbar.make(view, "Ошибка: заполните все поля формы", Snackbar.LENGTH_LONG).show();
                }
            }
        });



        return rootView;
    }

    private void getLastName() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);
                masterLastname = value.lastname;
                masterName = value.name;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });
    }

    private void writeNewMech() {
        repairs.cause = String.valueOf(edCause.getText());
        repairs.date = String.valueOf(edDate.getText());
        repairs.QR = String.valueOf(edQR.getText());
        repairs.Mlastname = String.valueOf(masterLastname);
        repairs.Mname = String.valueOf(masterName);
        repairs.name = String.valueOf(edName.getText());
        String nameRepair = String.valueOf(edDate.getText());
        myRef2 = database.getReference("repairs/" + String.valueOf(edQR.getText()) + "/");


        mech.masterLastname = String.valueOf(masterLastname);
        mech.masterName = String.valueOf(masterName);
        mech.QR = String.valueOf(edQR.getText());
        mech.kind = String.valueOf(edKind.getText());
        mech.date = String.valueOf(edDate.getText());
        mech.firm = String.valueOf(edFirm.getText());
        mech.name = String.valueOf(edName.getText());
        mech.address = String.valueOf(edAddress.getText());
        mech.cause = String.valueOf(edCause.getText());
        myRef1.child(mech.QR).setValue(mech);
        myRef2.child(nameRepair.substring(0, 2) + nameRepair.substring(3, 5) + nameRepair.substring(8, 10)).setValue(repairs);
    }


    public void checkDB() {
        myRef1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        collectAllMechanism((Map<FirebaseClassMechanism, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    private void collectAllMechanism(Map<FirebaseClassMechanism, Object> mechanism) {
        if (mechanism != null) {
            for (Map.Entry<FirebaseClassMechanism, Object> entry : mechanism.entrySet()) {
                Map singleUser = (Map) entry.getValue();
                if (singleUser.containsValue(QRcode)) {
                    edName.setText(String.valueOf(singleUser.get("name")));
                    edDate.setText(String.valueOf(singleUser.get("date")));
                    edFirm.setText(String.valueOf(singleUser.get("firm")));
                    edKind.setText(String.valueOf(singleUser.get("kind")));
                    edAddress.setText(String.valueOf(singleUser.get("address")));
                    edCause.setText(String.valueOf(singleUser.get("cause")));
                    edQR.setText(String.valueOf(singleUser.get("QR")));

                    TVLdate.setVisibility(View.GONE);
                    TVLcause.setVisibility(View.GONE);
                    ButtonHistory.setVisibility(View.VISIBLE);
                }
            }
        }

    }
    public interface One {
        void onButtonSelected(int fragIndex);
    }
}