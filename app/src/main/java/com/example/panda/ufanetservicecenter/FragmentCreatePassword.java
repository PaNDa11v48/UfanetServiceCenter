package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentCreatePassword extends Fragment implements View.OnClickListener {

    EditText editText;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    String firstPassword;
    String secondaryPassword;
    byte coutPass = 0;
    TextView textPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_create_key_one, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Регистрация");

        TextView text1 = rootView.findViewById(R.id.textView1);
        TextView text2 = rootView.findViewById(R.id.textView2);
        TextView text3 = rootView.findViewById(R.id.textView3);
        TextView text4 = rootView.findViewById(R.id.textView4);
        TextView text5 = rootView.findViewById(R.id.textView5);
        TextView text6 = rootView.findViewById(R.id.textView6);
        TextView text7 = rootView.findViewById(R.id.textView7);
        TextView text8 = rootView.findViewById(R.id.textView8);
        TextView text9 = rootView.findViewById(R.id.textView9);
        TextView text0 = rootView.findViewById(R.id.textView0);
        textPass = rootView.findViewById(R.id.textView11);
        final ImageView imageView = rootView.findViewById(R.id.imageView2);
        editText = rootView.findViewById(R.id.editText);
        image1 = rootView.findViewById(R.id.image1);
        image2 = rootView.findViewById(R.id.image2);
        image3 = rootView.findViewById(R.id.image3);
        image4 = rootView.findViewById(R.id.image4);
        image5 = rootView.findViewById(R.id.image5);

        text0.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
        text7.setOnClickListener(this);
        text8.setOnClickListener(this);
        text9.setOnClickListener(this);
        imageView.setOnClickListener(this);
        editText.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView0:
                setEditText(0);
                break;
            case R.id.textView1:
                setEditText(1);
                break;
            case R.id.textView2:
                setEditText(2);
                break;
            case R.id.textView3:
                setEditText(3);
                break;
            case R.id.textView4:
                setEditText(4);
                break;
            case R.id.textView5:
                setEditText(5);
                break;
            case R.id.textView6:
                setEditText(6);
                break;
            case R.id.textView7:
                setEditText(7);
                break;
            case R.id.textView8:
                setEditText(8);
                break;
            case R.id.textView9:
                setEditText(9);
                break;
            case R.id.imageView2:
                if (editText.getText().length() != 0) {
                    StringBuffer str = new StringBuffer(editText.getText().toString());
                    str.delete(editText.getText().length() - 1, editText.getText().length());
                    editText.setText(str.toString());
                }
                changeImageDel();
                break;
        }
    }


    public void setEditText(int number) {

        int lengthEdit = editText.getText().length();

        if (lengthEdit < 5) {
            editText.setText(editText.getText() + String.valueOf(number));
            vibrate();
            changeImage();
            if (lengthEdit == 4) CurrentPassword();
        }
    }


    public void CurrentPassword() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                coutPass++;
                textPass.setText("Повторите ранее введенный код");
                if (coutPass == 1) {
                    firstPassword = String.valueOf(editText.getText());
                    changeImageAllDel();
                }

                if (coutPass == 2) {
                    secondaryPassword = String.valueOf(editText.getText());
                    changeImageAllDel();
                    if (secondaryPassword.equals(firstPassword)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Регистрация успешно завершена", Snackbar.LENGTH_LONG).show();
                        FragmentRegister.One listener1 = (FragmentRegister.One) getActivity();
                        listener1.onButtonSelected(3);
                        SharedPreferences SharedPreferencesID;
                        SharedPreferencesID = getActivity().getSharedPreferences("PasswordID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
                        editor2.putString("PasswordID", secondaryPassword);
                        editor2.apply();
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Коды не совпадают", Snackbar.LENGTH_LONG).show();
                        coutPass = 0;
                        secondaryPassword = "";
                        firstPassword = "";
                        textPass.setText("Придумайте 5-значный код");
                    }
                }
            }
        }, 200);
    }

    private void changeImageAllDel() {
        image1.setImageResource(R.drawable.circle_false);
        image2.setImageResource(R.drawable.circle_false);
        image3.setImageResource(R.drawable.circle_false);
        image4.setImageResource(R.drawable.circle_false);
        image5.setImageResource(R.drawable.circle_false);
        editText.setText("");
    }

    public void vibrate() {
        long mills = 10L;
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(mills);
    }

    public void changeImage() {
        switch (editText.getText().length()) {
            case 1:
                image1.setImageResource(R.drawable.circle_true);
                break;
            case 2:
                image2.setImageResource(R.drawable.circle_true);
                break;
            case 3:
                image3.setImageResource(R.drawable.circle_true);
                break;
            case 4:
                image4.setImageResource(R.drawable.circle_true);
                break;
            case 5:
                image5.setImageResource(R.drawable.circle_true);
                break;
            default:
                break;
        }
        vibrate();

    }

    public void changeImageDel() {
        switch (editText.getText().length()) {
            case 0:
                image1.setImageResource(R.drawable.circle_false);
                break;
            case 1:
                image2.setImageResource(R.drawable.circle_false);
                break;
            case 2:
                image3.setImageResource(R.drawable.circle_false);
                break;
            case 3:
                image4.setImageResource(R.drawable.circle_false);
                break;
            case 4:
                image5.setImageResource(R.drawable.circle_false);
                break;
            default:
                break;
        }
        vibrate();
    }


    public interface One {
        void onButtonSelected(int fragIndex);
    }
}