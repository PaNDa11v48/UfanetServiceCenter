package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentEnter extends Fragment implements View.OnClickListener {
    private static final String TAG = "SiginIn";
    private FirebaseAuth mAuth;
    EditText edEmail;
    EditText edPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Вход");

        edEmail = rootView.findViewById(R.id.email);
        edPassword = rootView.findViewById(R.id.password);
        Button btnEnter = rootView.findViewById(R.id.enter);
        btnEnter.setOnClickListener(this);

        TextView btnRegister = rootView.findViewById(R.id.sign_up);
        btnRegister.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            SharedPreferences SharedPreferencesID;
            SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
            editor2.putString("sID", currentUser.getUid());
            editor2.apply();
        }
    }

    public void onSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    updateUI(null);
                    nextFragment();
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Snackbar.make(getView(), "Ошибка: некорректный логин или пароль", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter:
                if(checkField() == true) {
                    onSignIn(String.valueOf(edEmail.getText()), String.valueOf(edPassword.getText()));
                }
                break;
            case R.id.sign_up:
                One listener1 = (One) getActivity();
                listener1.onButtonSelected(1);
                break;
        }
    }

    public interface One {
        void onButtonSelected(int fragIndex);
    }

    public void nextFragment() {
        One listener1 = (One) getActivity();
        listener1.onButtonSelected(8);
    }


    public boolean checkField() {
        boolean boolInf = false;
                if ((edEmail.getText().length() != 0)) {
                    if ((edPassword.getText().length() != 0)) {
                        boolInf = true;
                    } else {
                        Snackbar.make(getView(), "Ошибка: заполните поле \"Пароль\"", Snackbar.LENGTH_LONG).show();
                    }
                }else {
                    Snackbar.make(getView(), "Ошибка: заполните поле \"Email\"", Snackbar.LENGTH_LONG).show();
                }


        return boolInf;
    }
}