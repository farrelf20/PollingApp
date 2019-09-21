package com.example.pollingapps.viewmodel

//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import android.arch.lifecycle.ViewModel
import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pollingapps.model.DataPemilih
import com.example.pollingapps.service.ApiOnly
import com.example.pollingapps.service.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataPemilihViewModel : ViewModel() {
    var lisPemilih: MutableLiveData<List<DataPemilih>>? = null
    val dataPemilih: LiveData<List<DataPemilih>>
        get() {
            if (lisPemilih == null) {
                lisPemilih = MutableLiveData()
                loadDataPemilih()
            }
            return lisPemilih!!
        }

    private fun loadDataPemilih() {
        val retrofit = Retrofit.Builder().baseUrl(Const.base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val apiData = retrofit.create(ApiOnly::class.java)
        val getData = apiData.getDataPemilih()
        getData.enqueue(object : Callback<List<DataPemilih>> {
            override fun onFailure(call: Call<List<DataPemilih>>, t: Throwable) {
                e("fail", "loadDataPemilih", t)
            }

            override fun onResponse(
                call: Call<List<DataPemilih>>,
                response: Response<List<DataPemilih>>
            ) {
                if (response.code() == 200) {
                    lisPemilih!!.value = response.body()
                } else {
                    e("response", response.message())
                }
            }

        })
    }
}