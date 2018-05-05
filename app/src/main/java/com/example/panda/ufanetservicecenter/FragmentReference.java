package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentReference extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Справочник");

        final String[] catNames = new String[]{
                "1.\tОбщие сведения", "2.\tФункциональное назначение", "3.\tРегистрация", "4.\tСмена пользователя", "5.\tСканирование",
                "6.\tОб авторе"};
        final ListView listView = rootView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_reference, catNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                SharedPreferences SharedPreferencesID;
                SharedPreferencesID = getActivity().getSharedPreferences("PositionReference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
                editor2.putString("PositionReference",String.valueOf(position));
                editor2.apply();
                One listener1 = (One) getActivity();
                listener1.onButtonSelected(12);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View view) {
    }


    public interface One {
        void onButtonSelected(int fragIndex);
    }
}