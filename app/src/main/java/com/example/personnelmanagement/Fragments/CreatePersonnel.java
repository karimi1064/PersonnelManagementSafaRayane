package com.example.personnelmanagement.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personnelmanagement.Api_Interface.RetrofitClient;
import com.example.personnelmanagement.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePersonnel extends Fragment
implements View.OnClickListener{
    private View view;
    private Button btnRegister;
    private EditText edtName, edtLastName;
    private String s;

    public CreatePersonnel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_personnel, container, false);
        initialize();
        btnRegister.setOnClickListener(this);

        return view;
    }

    private void initialize() {
        btnRegister = view.findViewById(R.id.btn_register);
        edtName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register){
            registerPersonnel();
        }
    }

    private void registerPersonnel(){
        String name, family;
        name = edtName.getText().toString().trim();
        family = edtLastName.getText().toString().trim();

        Call<ResponseBody> call = RetrofitClient
                .getmInstance()
                .getApi()
                .sendPersonnel(name,family);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                edtName.setText("");
                edtLastName.setText("");
                Toast.makeText(getContext(), R.string.done, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
