package com.example.panda.ufanetservicecenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityRegisterMechanism extends AppCompatActivity implements FragmentRegisterMechanism.One, CustomDialogAdd.One, FragmentAllRepairs.One{
    Toolbar toolbar;
    String QRcode;
    boolean check;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_register_mechanism);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Информация о технике");
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        QRcode = intent.getStringExtra("QRcode");
        check = intent.getBooleanExtra("booleanCheckDB", false);


        bundle = new Bundle();
        bundle.putString("QRcode", QRcode);
        bundle.putBoolean("booleanCheckDB", check);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fr = fragmentManager.beginTransaction();
        FragmentRegisterMechanism fragment1 = new FragmentRegisterMechanism ();
        fragment1.setArguments(bundle);
        fr.add(R.id.container, fragment1, "fragment_enter");
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
            case 5:
                toolbar.setTitle("Вход");
                FragmentEnter fragment5 = new FragmentEnter();
                ft.replace(R.id.container, fragment5, "fragment5");
                break;
            case 9:
                toolbar.setTitle("История ремонта");
                FragmentAllRepairs fragment9 = new FragmentAllRepairs();
                fragment9.setArguments(bundle);
                ft.replace(R.id.container, fragment9, "fragment9");
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
        }
        ft.addToBackStack(null);
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