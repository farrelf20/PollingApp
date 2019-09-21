package com.example.pollingapps.view

//import android.arch.lifecycle.Observer
//import android.arch.lifecycle.ViewModelProviders
//import android.databinding.DataBindingUtil
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pollingapps.R
import com.example.pollingapps.adapter.ListCalonAdapter
import com.example.pollingapps.viewmodel.DataCalonViewModel
import it.sephiroth.android.library.tooltip.Tooltip
import kotlinx.android.synthetic.main.calon_kandidat.*

class ListCalonActivity: AppCompatActivity() {
    var rcView : RecyclerView? = null
    var calonAdapter: ListCalonAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calon_kandidat)
        Tooltip.make(this, Tooltip.Builder(101).anchor(tooltip, Tooltip.Gravity.BOTTOM).closePolicy(Tooltip.ClosePolicy().insidePolicy(true, false).outsidePolicy(true, false), 4000)
            .activateDelay(1000).showDelay(400).text("Klik Salah Satu Calon Untuk Vote").maxWidth(400)
            .withArrow(true).withOverlay(true).build()).show()
        rcView = findViewById(R.id.rcCalon)
        rcView!!.setHasFixedSize(true)
        rcView!!.layoutManager = LinearLayoutManager(this)
        val viewModel = ViewModelProviders.of(this).get(DataCalonViewModel::class.java)
        viewModel.dataCalon.observeForever{
            e("tag", it!!.get(0).nama)
            calonAdapter = ListCalonAdapter(this@ListCalonActivity, it)
            rcView!!.adapter = calonAdapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}