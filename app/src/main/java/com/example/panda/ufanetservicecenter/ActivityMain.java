package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityMain extends AppCompatActivity implements FragmentEnter.One, FragmentRegister.One, FragmentPassword.One, FragmentInfo.One, FragmentCreatePassword.One, FragmentReference.One {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_mian);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Сервисный центр \"Ufanet\"");
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fr = fragmentManager.beginTransaction();
        FragmentEnter fragment1 = new FragmentEnter();
        FragmentPassword fragment2 = new FragmentPassword();

        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getSharedPreferences("sID", Context.MODE_PRIVATE);
        String IDtext = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));
        String zero = "0";

        if (savedInstanceState == null) {
            if (IDtext.equals(zero)) {
                fr.add(R.id.container, fragment1, "fragment_enter");
            } else {
                fr.add(R.id.container, fragment2, "fragment_password");
            }
        }
        fr.commit();
    }

    @Override
    public void onButtonSelected(int fragIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (fragIndex) {
            case 1:
                toolbar.setTitle("Регистрация");
                FragmentRegister fragment1 = new FragmentRegister();
                ft.replace(R.id.container, fragment1, "fragment1");
                break;
            case 2:
                toolbar.setTitle("Сервисный центр \"Ufanet\"");
                FragmentPassword fragment2 = new FragmentPassword();
                ft.replace(R.id.container, fragment2, "fragment2");
                break;
            case 3:
                toolbar.setTitle("Информация");
                FragmentInfo fragment3 = new FragmentInfo();
                ft.replace(R.id.container, fragment3, "fragment3");
                break;
            case 4:
                toolbar.setTitle("Камера");
                FragmentCamera fragment4 = new FragmentCamera();
                ft.replace(R.id.container, fragment4, "fragment4");
                break;
            case 5:
                toolbar.setTitle("Вход");
                FragmentEnter fragment5 = new FragmentEnter();
                ft.replace(R.id.container, fragment5, "fragment5");
                break;
            case 6:
                toolbar.setTitle("Регистрация");
                FragmentCreatePassword fragment6 = new FragmentCreatePassword();
                ft.replace(R.id.container, fragment6, "fragment6");
                break;
            case 7:
                break;
            case 8:
                toolbar.setTitle("Вход");
                FragmentCreatePasswordAunt fragment8 = new FragmentCreatePasswordAunt();
                ft.replace(R.id.container, fragment8, "fragment8");
                break;
            case 9:
                toolbar.setTitle("Вся техника");
                FragmentAllRepairs fragment9 = new FragmentAllRepairs();
                ft.replace(R.id.container, fragment9, "fragment9");
                break;
            case 10:
                Intent intent = new Intent(this, ActivityRegisterMechanism.class);
                startActivity(intent);
                break;
            case 11:
                toolbar.setTitle("Справочник");
                FragmentReference fragment11 = new FragmentReference();
                ft.replace(R.id.container, fragment11, "fragment11");
                break;
            case 12:
                toolbar.setTitle("Справочник");
                FragmentReferenceInfo fragment12 = new FragmentReferenceInfo();
                ft.replace(R.id.container, fragment12, "fragment12");
                break;
            case 13:
                toolbar.setTitle("Ручной ввод");
                FragmentHandInput fragment13 = new FragmentHandInput();
                ft.replace(R.id.container, fragment13, "fragment13");
                break;
        }
        if(fragIndex != 4) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.change_user) {
            onButtonSelected(5);
            return true;
        }
        if (id == R.id.register) {
            onButtonSelected(1);
            return true;
        }
        if (id == R.id.reference) {
            onButtonSelected(11);
            return true;
        }
        if (id == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}