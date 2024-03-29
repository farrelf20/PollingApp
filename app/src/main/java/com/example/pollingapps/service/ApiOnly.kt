package com.example.pollingapps.service

import com.example.pollingapps.model.DataCalon
import com.example.pollingapps.model.DataPemilih
import com.example.pollingapps.model.Vote
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiOnly {

        @GET("datacalon")
        fun getDataCalon() :  Call<List<DataCalon>>
        @GET("pemilih")
        fun getDataPemilih(): Call<List<DataPemilih>>
        @POST("vote")
        @FormUrlEncoded
        fun postVote(@Field("id_calon")id_calon: String,
                     @Field("id_pemilih")id_pemilih: String): Call<Vote>

}