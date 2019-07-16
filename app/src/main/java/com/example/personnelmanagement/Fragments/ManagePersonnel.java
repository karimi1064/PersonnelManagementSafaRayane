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

import com.example.personnelmanagement.Adapter.AdapterManagePersonnel;
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

public class ManagePersonnel extends Fragment
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        SearchView.OnQueryTextListener {
    View view;
    private ImageView btnShare;
    Spinner spinnerSearch;
    String[] searchBy = {"by Code", "by Family"};
    ArrayAdapter arrayAdapterSearch;
    private Api interfaceApi;
    private RecyclerView recyclerPersonnelList;
    private List<DataModel> dataModelObject;
    private AdapterManagePersonnel adapterManagePersonnel;
    private SearchView searchView;
    private int searchState = 0;
    private ProgressBar progressBar;

    public ManagePersonnel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_personnel, container, false);
        initViews();
        arrayAdapterSearch = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, searchBy);
        arrayAdapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(arrayAdapterSearch);
        spinnerSearch.setOnItemSelectedListener(this);
        initRecycler();
        searchView.setOnQueryTextListener(this);
        btnShare.setOnClickListener(this);

        return view;
    }

    private void initRecycler() {
        interfaceApi = RetrofitClient.getmInstance().getApi();
        Call<List<DataModel>> listCall = interfaceApi.getInterfaceApi();
        listCall.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                progressBar = view.findViewById(R.id.progress_personnel_list);
                if (response.isSuccessful()) {
                    dataModelObject = response.body();

                    recyclerPersonnelList.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerPersonnelList.setLayoutManager(linearLayoutManager);
                    adapterManagePersonnel = new AdapterManagePersonnel(getContext(), dataModelObject);
                    recyclerPersonnelList.setAdapter(adapterManagePersonnel);
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "اتصال شما با اینترنت برقرار نیست", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        progressBar = view.findViewById(R.id.progress_manage_personnel);
        btnShare = view.findViewById(R.id.img_share_list);
        recyclerPersonnelList = view.findViewById(R.id.manage_personnel_list);
        spinnerSearch = view.findViewById(R.id.spinner_search_manage);
        searchView = view.findViewById(R.id.search_manage_personnel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_share_list:
                String personnelList = "";
                for (int i=0; i<dataModelObject.size();i++) {
                    personnelList += "\n" + getString(R.string.first_name) + " "
                            + dataModelObject.get(i).getName()
                            + "\n" + getString(R.string.last_name) + " "
                            + dataModelObject.get(i).getFamily() + "\n";
                }
                Intent ShareIntent = new Intent(Intent.ACTION_SEND);
                ShareIntent.setType("text/plain");
                ShareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.personnel_list));
                ShareIntent.putExtra(Intent.EXTRA_TEXT, personnelList);
                startActivity(Intent.createChooser(ShareIntent, "Share via"));
                break;
        }
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
        adapterManagePersonnel.setFilter(filterModelList);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_search_manage:
                searchState = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
}