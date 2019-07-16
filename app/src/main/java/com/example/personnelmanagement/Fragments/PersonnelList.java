package com.example.personnelmanagement.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personnelmanagement.Adapter.AdapterPersonnelList;
import com.example.personnelmanagement.Api_Interface.Api;
import com.example.personnelmanagement.Api_Interface.RetrofitClient;
import com.example.personnelmanagement.Models.DataModel;
import com.example.personnelmanagement.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonnelList extends Fragment
        implements AdapterView.OnItemSelectedListener,
        SearchView.OnQueryTextListener {
    private View view;
    Spinner spinnerSearch;
    String[] searchBy = {"by Code", "by Family"};
    ArrayAdapter arrayAdapterSearch;
    private Api interfaceApi;
    private RecyclerView recyclerPersonnelList;
    private List<DataModel> dataModelObject;
    private AdapterPersonnelList adapterPersonnelList;
    private SearchView searchView;
    private int searchState = 0;
    private ProgressBar progressBar;

    public PersonnelList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personnel_list, container, false);
        initialize();
        arrayAdapterSearch = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, searchBy);
        arrayAdapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(arrayAdapterSearch);

        spinnerSearch.setOnItemSelectedListener(this);
        initRecycler();
        searchView.setOnQueryTextListener(this);

        return view;
    }

    private void initRecycler() {
        interfaceApi = RetrofitClient.getmInstance().getApi();
        Call<List<DataModel>> listCall = interfaceApi.getInterfaceApi();
        listCall.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    dataModelObject = response.body();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerPersonnelList.setLayoutManager(linearLayoutManager);
                    adapterPersonnelList = new AdapterPersonnelList(getContext(), dataModelObject);
                    recyclerPersonnelList.setAdapter(adapterPersonnelList);
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), R.string.connect_internet_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize() {
        progressBar = view.findViewById(R.id.progress_personnel_list);
        recyclerPersonnelList = view.findViewById(R.id.personnel_list);
        spinnerSearch = view.findViewById(R.id.spinner_search);
        searchView = view.findViewById(R.id.search_personnel_list);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_search:
                searchState = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private List<DataModel> filter(List<DataModel> p1, String query) {
        query = query.toLowerCase().trim();
        final List<DataModel> filteredModeList = new ArrayList<>();
        for (DataModel dataModel : p1) {
            final String text = dataModel.getName();
            if (text.contains(query)) {
                filteredModeList.add(dataModel);
            }
        }
        return filteredModeList;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        final List<DataModel> filterModelList = filter(dataModelObject, s);
        adapterPersonnelList.setFilter(filterModelList);
        return true;
    }
}