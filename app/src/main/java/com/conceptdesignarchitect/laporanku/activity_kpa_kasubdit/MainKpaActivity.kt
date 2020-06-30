package com.conceptdesignarchitect.laporanku.activity_kpa_kasubdit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.activity_ppk.LihatLaporanActivity
import com.conceptdesignarchitect.laporanku.activity_ppk.LoginActivity
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.CeknotifResponse
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main_kpa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainKpaActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        cek_report()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kpa)

        idUserEmail.text = SharedPrefManager.getInstance(this).user.email

        id_tombol_report.setOnClickListener {
            intent = Intent(applicationContext, LihatLaporanActivity::class.java)
            startActivity(intent)
        }

        id_btn_profil.setOnClickListener {
            SharedPrefManager.getInstance(this).clear()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun cek_report() {
        RetrofitClient.instance.ceknotif(SharedPrefManager.getInstance(this).user.id)
            .enqueue(object : Callback<CeknotifResponse> {
                override fun onFailure(call: Call<CeknotifResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<CeknotifResponse>,
                    response: Response<CeknotifResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.status == "Berhasil" && response.body()?.notif.toString() != "0"){
                            response.body()?.notif?.let { id_badge_report.setBadgeValue(it) }
                        }else{
                            Toast.makeText(applicationContext, ""+response.body()?.status, Toast.LENGTH_SHORT).show()
                            response.body()?.notif?.let { id_badge_report.setBadgeValue(it) }
                        }
                    }
                    else{
                        Toast.makeText(applicationContext, "Gagal mendapatkan pembaruan." , Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }
}
