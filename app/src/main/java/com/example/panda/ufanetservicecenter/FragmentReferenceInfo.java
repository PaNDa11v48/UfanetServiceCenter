package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentReferenceInfo extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reference_info ,container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Справочник");

        TextView txt = rootView.findViewById(R.id.textView14);
        Toolbar toolbar;
        toolbar = getActivity().findViewById(R.id.toolbar);


        SharedPreferences SharedPreferencesFragment;
        SharedPreferencesFragment = getActivity().getSharedPreferences("PositionReference", Context.MODE_PRIVATE);
        String IDtext = SharedPreferencesFragment.getString("PositionReference", String.valueOf(Context.MODE_PRIVATE));

        switch (Integer.valueOf(IDtext)){
            case 0:
                txt.setText("\t\t\t\t Приложение для автоматизация учёта ремонта оборудования в сервисном центре АО «Уфанет» " +
                        "предназначен для:\n" +
                        "\t\t\t\t1.\t Быстрого доступа к информации о технике.\n" +
                        "\t\t\t\t2.\t Отслеживание прогресса выполнения ремонта техники.\n" +
                        "\t\t\t\t3.\t Создания базы данных для внесения информации о выполненной работе по ремонту техники.\n" +
                        "\t\t\t\t4.\t Удобного редактирование информации в базе данных техники.\n"
                );
                toolbar.setTitle("Общие сведения");
                break;
            case 1:
                txt.setText("2");
                toolbar.setTitle("Функциональное назначение");
                break;
            case 2:
                txt.setText("3");
                toolbar.setTitle("Регистрация");
                break;
            case 3:
                txt.setText("4");
                toolbar.setTitle("Смена пользователя");
                break;
            case 4:
                txt.setText("5");
                toolbar.setTitle("Сканирование");
                break;
            case 5:
                txt.setText("6");
                toolbar.setTitle("Об авторе");
                break;
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }
}