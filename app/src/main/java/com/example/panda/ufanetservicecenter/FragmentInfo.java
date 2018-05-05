package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentInfo extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentInfo";
    TextView text1;
    TextView text2;
    TextView text3;
    DatabaseReference myRef;
    DatabaseReference myRef1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_info, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Информация");



        Button btn2 = fragView.findViewById(R.id.button2);
        Button btn3 = fragView.findViewById(R.id.button3);
        Button btn5 = fragView.findViewById(R.id.button5);


        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn5.setOnClickListener(this);

        text1 = fragView.findViewById(R.id.textView1);
        text2 = fragView.findViewById(R.id.textView2);
        text3 = fragView.findViewById(R.id.textView3);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        String ans1 = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));

        myRef = database.getReference("users/" + ans1);
        myRef1 = database.getReference("users/");

        ReadFirebase();
        return fragView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                One listener2 = (One) getActivity();
                listener2.onButtonSelected(13);
                break;
            case R.id.button3:
                One listener3 = (One) getActivity();
                listener3.onButtonSelected(4);
                break;
            case R.id.button5:
                One listener5 = (One) getActivity();
                listener5.onButtonSelected(11);
                break;

        }
    }

    public interface One {
        void onButtonSelected(int fragIndex);
    }


    public void ReadFirebase() {


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);
                text1.setText(value.name);
                text2.setText(value.lastname);
                text3.setText(value.email);

            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });


    }
}