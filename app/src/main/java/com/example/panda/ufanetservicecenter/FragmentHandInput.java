package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class FragmentHandInput extends Fragment {

    EditText QRcode;
    ImageButton next;
    String QRtext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hand_input, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ручной ввод");

        QRcode = rootView.findViewById(R.id.QRcodeInputHands);
        next = rootView.findViewById(R.id.Next);


        QRcode.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(QRcode, InputMethodManager.SHOW_IMPLICIT);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(QRcode.getText().length() != 0) {
                    QRtext = String.valueOf(QRcode.getText());
                    Intent intent = new Intent(getActivity(), ActivityRegisterMechanism.class);
                    intent.putExtra("QRcode",QRtext);
                    intent.putExtra("booleanCheckDB", true);
                    startActivity(intent);

                }else{
                    Snackbar.make(view, "Ошибка: заполните поле \"QR-код\"", Snackbar.LENGTH_LONG).show();
                }
            }
        });



        return rootView;
    }
}