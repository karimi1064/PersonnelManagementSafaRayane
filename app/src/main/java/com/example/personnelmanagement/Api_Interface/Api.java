package com.example.personnelmanagement.Api_Interface;

import com.example.personnelmanagement.Models.DataModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface Api {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("personnel")
    Call<ResponseBody> sendPersonnel(
            @Field("name") String name,
            @Field("family") String family);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("enterexit")
    Call<ResponseBody> sendTime(
            @Field("state") int state,
            @Field("personnelId") int personnelId);

    @GET("personnel")
    Call<List<DataModel>> getInterfaceApi();

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("edit")
    Call<ResponseBody> editPerson(
            @Field("personnelId") int personnelId,
            @Field("name") String name,
            @Field("family") String family);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @DELETE("delete")
    Call<ResponseBody> deletePerson(
            @Field("personnelId") int personnelId);
}