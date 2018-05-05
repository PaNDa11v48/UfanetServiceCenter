package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentRegister extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentRegister";
    private FirebaseAuth mAuth;
    EditText edEmail;
    EditText edPassword;
    EditText edName;
    EditText edLastname;
    DatabaseReference myRef;
    String AuthenticationID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Регистрация");

        edEmail = rootView.findViewById(R.id.email);
        edPassword = rootView.findViewById(R.id.password);
        edName = rootView.findViewById(R.id.name);
        edLastname = rootView.findViewById(R.id.lastname);

        Button btnEnter = rootView.findViewById(R.id.enter);
        btnEnter.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter:
                if (checkField(view) == true) {
                    onCreateAcc(String.valueOf(edEmail.getText()), String.valueOf(edPassword.getText()));
                }
                break;
        }

    }

    public interface One {
        void onButtonSelected(int fragIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        com.google.firebase.auth.FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(com.google.firebase.auth.FirebaseUser currentUser) {
        if (currentUser != null) {
            SharedPreferences SharedPreferencesID;
            SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
            editor2.putString("sID", currentUser.getUid());
            editor2.apply();
        }
    }

    public void onCreateAcc(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    com.google.firebase.auth.FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    AuthenticationID = user.getUid();
                    writeNewUser();
                    One listener1 = (One) getActivity();
                    listener1.onButtonSelected(6);
                } else {
                    Snackbar.make(getView(), "Ошибка: некорректный Email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    updateUI(null);
                }
            }
        });
    }

    private void writeNewUser() {
        FirebaseClassUser user = new FirebaseClassUser();
        user.email = String.valueOf(edEmail.getText());
        user.password = String.valueOf(edPassword.getText());
        user.lastname = String.valueOf(edLastname.getText());
        user.name = String.valueOf(edName.getText());
        myRef.child("users/" + AuthenticationID).setValue(user);

    }

    public boolean checkField(View view) {
        boolean boolInf = false;
        if ((edName.getText().length() != 0)) {
            if ((edLastname.getText().length() != 0)) {
                if ((edEmail.getText().length() != 0)) {
                    if ((edPassword.getText().length() != 0)) {
                        boolInf = true;
                    } else {
                        Snackbar.make(view, "Ошибка: заполните поле \"Пароль\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, "Ошибка: заполните поле \"Email\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            } else {
                Snackbar.make(view, "Ошибка: заполниет поле \"Фамилия\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        } else {

            Snackbar.make(view, "Ошибка: заполниет поле \"Имя\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        return boolInf;
    }
}