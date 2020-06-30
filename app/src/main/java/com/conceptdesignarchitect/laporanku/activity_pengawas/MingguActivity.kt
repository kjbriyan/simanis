package com.conceptdesignarchitect.laporanku.activity_pengawas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.adapter.MingguAdapter
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.DataMingguItem
import com.conceptdesignarchitect.laporanku.models.MingguResponse
import kotlinx.android.synthetic.main.activity_minggu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MingguActivity : AppCompatActivity() {

    fun onItemClicked(get: DataMingguItem?){
        val inte = intent
//        Toast.makeText(this@MingguActivity, "klick "+intent.getStringExtra("proyek"), Toast.LENGTH_LONG).show()
        val intent = Intent(this, RealisasiActivity::class.java)
        intent.putExtra("idminggu", get?.id)
        intent.putExtra("minggu", get?.minggu)
        intent.putExtra("proyek", inte.getStringExtra("proyek"))
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minggu)

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.mingguKe(intent.getStringExtra("proyek"))
            .enqueue(object: Callback<MingguResponse> {
                override fun onFailure(call: Call<MingguResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                @Suppress("UNCHECKED_CAST")
                override fun onResponse(call: Call<MingguResponse>, response: Response<MingguResponse>) {
                    Log.i("Response", response.body().toString());
                    if(response.isSuccessful){
                        rvMinggu.adapter = MingguAdapter(response.body()?.dataMinggu as List<DataMingguItem>?,
                            this@MingguActivity
                        )
                    }else{
                        Toast.makeText(applicationContext, "Maaf terjadi Kesalahan.." , Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
