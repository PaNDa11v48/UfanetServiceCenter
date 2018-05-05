package com.example.panda.ufanetservicecenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentAllRepairs extends Fragment {

    private static final String TAG = "AllRepairs";
    List<String> allUsers = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ListView listView;
    FloatingActionButton fab;
    String QRcode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repair, container, false);
        listView = rootView.findViewById(R.id.listView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            QRcode = bundle.getString("QRcode");
        }
        myRef = database.getReference("repairs/" + QRcode);

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        collectAllUsers((Map<FirebaseClassRepairs, Object>) dataSnapshot.getValue());
                        fillArrayAdapter();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View itemClicked, int position, long id) {
                myRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextView textView = (TextView) itemClicked;
                                String strText = textView.getText().toString();
                                CustomDialogInfo cdd = new CustomDialogInfo(getActivity());
                                cdd.setMyRef(QRcode);
                                cdd.setDate(strText);
                                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                cdd.show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

            }
        });


        fab = rootView.findViewById(R.id.addRepairButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogAdd cdd = new CustomDialogAdd(getActivity());
                cdd.setMyRef(QRcode);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

        return rootView;
    }

    private void collectAllUsers(Map<FirebaseClassRepairs, Object> mechanism) {
        if (mechanism != null) {
            for (Map.Entry<FirebaseClassRepairs, Object> entry : mechanism.entrySet()) {
                Map singleUser = (Map) entry.getValue();
                allUsers.add(String.valueOf(singleUser.get("date")));
            }
        }else{
            allUsers.add("История ремонта пуста.  Нажмите кнопку \"Добавить\", чтобы внести информация о ремонте");
        }
    }


    private void fillArrayAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_repair, allUsers);
        listView.setAdapter(adapter);
    }

    public interface One {
        void onButtonSelected(int fragIndex);
    }

}