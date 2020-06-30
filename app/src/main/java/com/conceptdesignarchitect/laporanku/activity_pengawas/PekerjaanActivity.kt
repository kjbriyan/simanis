package com.conceptdesignarchitect.laporanku.activity_pengawas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.adapter.ProyeksAdapter
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.DataProyekItem
import com.conceptdesignarchitect.laporanku.models.ProyekResponse
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager
import kotlinx.android.synthetic.main.activity_pekerjaan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PekerjaanActivity : AppCompatActivity() {

    private val goodModelArrayList: ArrayList<DataProyekItem>? = null
    private val playerNames = ArrayList<String>()

    fun onItemClicked(get: DataProyekItem?) {
//        Toast.makeText(this@PekerjaanActivity, "klick "+get?.proyek, Toast.LENGTH_LONG).show()
        val intent = Intent(this, MingguActivity::class.java)
        intent.putExtra("proyek", get?.proyek)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pekerjaan)

        RetrofitClient.instance.pilihProyek(SharedPrefManager.getInstance(this).user.id)
            .enqueue(object : Callback<ProyekResponse> {
                override fun onFailure(call: Call<ProyekResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                @Suppress("UNCHECKED_CAST")
                override fun onResponse(
                    call: Call<ProyekResponse>,
                    response: Response<ProyekResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if (response.isSuccessful) {
                        rvPekerjaan.adapter = ProyeksAdapter(
                            response.body()?.dataProyek as List<DataProyekItem>?,
                            this@PekerjaanActivity
                        )
//                        var list = response.body()?.dataProyek as List<DataProyekItem>?
//
//                        for (i in list!!.indices) {
//                            playerNames.add(list[i].proyek.toString())
//                        }
//                        Toast.makeText(applicationContext, "he " +playerNames[0], Toast.LENGTH_LONG).show()
//                        Toast.makeText(applicationContext, "hek " +list[1], Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Maaf Terjadi Kesalahan..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
    }
}
