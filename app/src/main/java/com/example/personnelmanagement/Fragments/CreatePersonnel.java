package com.example.personnelmanagement.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personnelmanagement.R;

public class CreatePersonnel extends Fragment {
    private View view;


    public CreatePersonnel() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_personnel, container, false);

        return view;
    }

}
