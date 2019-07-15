package com.example.personnelmanagement.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personnelmanagement.Models.Constant;
import com.example.personnelmanagement.R;


public class AboutUs extends Fragment {
    private TextView txtAppDescription, txtEmail, txtDeveloperName;
    private View view;

    public AboutUs() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String fontStyle, typeFacePath = "fonts/";
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        initialize();
        float size = Constant.fontSize;
        fontStyle = Constant.fontStyle;
        Typeface typeface = Typeface.createFromAsset(getActivity().getApplicationContext()
                .getAssets(), typeFacePath + fontStyle);

        txtAppDescription.setTextSize(size);
        txtAppDescription.setTypeface(typeface);
        txtEmail.setTextSize(size);
        txtEmail.setTypeface(typeface);
        txtDeveloperName.setTextSize(size);
        txtDeveloperName.setTypeface(typeface);

        return view;
    }

    private void initialize() {
        txtAppDescription = view.findViewById(R.id.txt_app_description);
        txtEmail = view.findViewById(R.id.txt_email);
        txtDeveloperName = view.findViewById(R.id.txt_my_name);
    }
}
