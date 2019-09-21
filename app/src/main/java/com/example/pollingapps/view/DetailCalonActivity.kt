package com.example.pollingapps.view

//import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.util.Log.i
import android.util.Log.w
import com.bumptech.glide.Glide
import com.example.pollingapps.R
import com.example.pollingapps.data.SharePref
import com.example.pollingapps.model.Vote
import com.example.pollingapps.service.ApiOnly
import com.example.pollingapps.service.Const
import com.example.pollingapps.viewmodel.VoteViewModel
import kotlinx.android.synthetic.main.vote_activity.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.view.ViewGroup
import android.view.LayoutInflater
//import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.berhasil.*
import kotlin.system.exitProcess
import android.R.attr.prompt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetailCalonActivity : AppCompatActivity() {
    var retrofit: Retrofit? = null
    private var set: SharePref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vote_activity)
        set = SharePref(this)
        val id = intent.getStringExtra("id_calon")
        val nama = intent.getStringExtra("nama")
        val visicalon = intent.getStringExtra("visi")
        val misicalon = intent.getStringExtra("misi")
        val id_pemilih = set!!.readSetting(Const.PREF_MY_ID)
        val foto = intent.getStringExtra("foto")
        w("tag", "$id & $id_pemilih")
        val fotourl = "http://172.16.10.10:8000/${foto}"
        visi.text = visicalon
        misi.text = misicalon
        calon_nama.text = nama
        Glide.with(this).load(fotourl).into(image_calon)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder().client(client).baseUrl(Const.base_url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val postData = retrofit!!.create(ApiOnly::class.java)
        vote.setOnClickListener {
            set!!.deleteAllSetting()
            postData.postVote("${id}", "${id_pemilih}").enqueue(object : Callback<Vote> {
                override fun onFailure(call: Call<Vote>, t: Throwable) {
                    i("y", "j" + t.cause)
                }

                override fun onResponse(call: Call<Vote>, response: Response<Vote>) {
                    i("tag", "test" + response.body().toString())
                    if (response.isSuccessful) {

                    }
                }

            })
            val layoutInflater = LayoutInflater.from(this)
            val promptView = layoutInflater.inflate(R.layout.berhasil, null)
            val alertD = AlertDialog.Builder(this).create()
            val btn = promptView.findViewById<Button>(R.id.buttonOk)
            btn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            alertD.setView(promptView)
            alertD.show()
        }
    }

}