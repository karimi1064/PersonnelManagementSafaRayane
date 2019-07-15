package com.example.personnelmanagement.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.personnelmanagement.R;

public class Home extends Fragment
        implements View.OnClickListener {
    private View view;
    private ImageButton btnCreate, btnList, btnManage;


    public Home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();
        btnCreate.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnManage.setOnClickListener(this);

        return view;
    }

    private void initialize() {
        btnCreate = view.findViewById(R.id.btn_create_personnel);
        btnList = view.findViewById(R.id.btn_personnel_list);
        btnManage = view.findViewById(R.id.btn_manage_personnel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_personnel:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new CreatePersonnel()).addToBackStack(null).commit();
                break;
            case R.id.btn_personnel_list:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new PersonnelList()).addToBackStack(null).commit();
                break;
            case R.id.btn_manage_personnel:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ManagePersonnel()).addToBackStack(null).commit();
                break;
        }
    }
}
