package com.example.personnelmanagement.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personnelmanagement.Api_Interface.RetrofitClient;
import com.example.personnelmanagement.Models.DataModel;
import com.example.personnelmanagement.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterManagePersonnel
        extends RecyclerView.Adapter<AdapterManagePersonnel.viewHolder> {

    private String s;
    private Context context;
    private List<DataModel> dataModel;

    public AdapterManagePersonnel(Context context, List<DataModel> dataModel) {
        this.context = context;
        this.dataModel = dataModel;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_personnel_list, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        final DataModel data_model = dataModel.get(position);
        // Glide.with(context).load(data_model.getImageUrl()).into(holder.productImg);
//        holder.btnEdit.setText(R.string.edit);
//        holder.btnDelete.setText(R.string.delete);
        holder.txtCode.setText("" + data_model.getId());
        holder.txtName.setText("" + data_model.getName());
        holder.txtLastName.setText("" + data_model.getFamily());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPersonnel(data_model.getId(),data_model.getName(),data_model.getFamily());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePersonnel(data_model.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txtCode, txtName, txtLastName;
        Button btnEdit, btnDelete;

        public viewHolder(View itemView) {
            super(itemView);
            txtCode = itemView.findViewById(R.id.txt_pl_code);
            txtName = itemView.findViewById(R.id.txt_pl_name);
            txtLastName = itemView.findViewById(R.id.txt_pl_last_name);
            btnEdit = itemView.findViewById(R.id.btn_enter);
            btnDelete = itemView.findViewById(R.id.btn_exit);
            btnEdit.setText(R.string.edit);
            btnDelete.setText(R.string.delete);
        }
    }

    private void editPersonnel(int id, String n, String f) {
        Call<ResponseBody> call = RetrofitClient
                .getmInstance()
                .getApi()
                .editPerson(id,n,f);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, R.string.done, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePersonnel(int id) {
        Call<ResponseBody> call = RetrofitClient
                .getmInstance()
                .getApi()
                .deletePerson(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, R.string.done, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFilter(List<DataModel> data) {
        dataModel = new ArrayList<>();
        dataModel.addAll(data);
        notifyDataSetChanged();
    }
}