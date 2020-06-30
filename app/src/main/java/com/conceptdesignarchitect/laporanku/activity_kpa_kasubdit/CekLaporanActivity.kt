package com.conceptdesignarchitect.laporanku.activity_kpa_kasubdit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.adapter.CeklaporanAdapter
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.DataLihatlaporanItem
import com.conceptdesignarchitect.laporanku.models.LihatlaporanResponse
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager
import kotlinx.android.synthetic.main.activity_cek_laporan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekLaporanActivity : AppCompatActivity() {

    fun onItemClicked(get: DataLihatlaporanItem?){
        val inte = intent
//        Toast.makeText(this, "klick "+get?.namaProyek, Toast.LENGTH_LONG).show()
        val intent = Intent(this, CekRealisasiActivity::class.java)
        intent.putExtra("idminggu", get?.idMinggu)
        intent.putExtra("minggu", get?.minggu)
        intent.putExtra("proyek", get?.namaProyek)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        RetrofitClient.instance.lihatlaporan(SharedPrefManager.getInstance(this).user.id)
            .enqueue(object: Callback<LihatlaporanResponse> {
                override fun onFailure(call: Call<LihatlaporanResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<LihatlaporanResponse>,
                    response: Response<LihatlaporanResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if(response.isSuccessful){
                        @Suppress("UNCHECKED_CAST")
                        rvLihatlaporan.adapter = CeklaporanAdapter(response.body()?.dataLihatlaporan as List<DataLihatlaporanItem>?,
                            this@CekLaporanActivity
                        )
                    }else{
                        Toast.makeText(applicationContext, "Maaf terjadi Kesalahan.." , Toast.LENGTH_LONG).show()
                    }
                }

            })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_laporan)
    }
}
