package com.example.pollingapps.viewmodel

//import android.arch.lifecycle.ViewModel
import androidx.lifecycle.ViewModel
import com.example.pollingapps.model.Vote
import com.example.pollingapps.service.ApiOnly
import com.example.pollingapps.service.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VoteViewModel: ViewModel() {
    var retrofit : Retrofit? = null
    fun postVote(): Retrofit?{
        if (retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(Const.base_url).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }

}