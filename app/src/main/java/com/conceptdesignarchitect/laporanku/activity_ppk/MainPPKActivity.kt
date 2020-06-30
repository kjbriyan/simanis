package com.conceptdesignarchitect.laporanku.activity_ppk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.activity_kpa_kasubdit.MainKpaActivity
import com.conceptdesignarchitect.laporanku.activity_ppk.MainPPKActivity.PreferenceHelper.customPreference
import com.conceptdesignarchitect.laporanku.activity_pengawas.MainPengawasActivity
import com.conceptdesignarchitect.laporanku.activity_ppspm.MainPpspmActivity
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.CeknotifResponse
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main_ppk.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPPKActivity : AppCompatActivity() {
//    dashboard untuk direksi
    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ppk)

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

//        id_tombol_report.setOnClickListener {  }

        val prefs = customPreference(this, CUSTOM_PREF_NAME)
//        prefs.proyek = "svdd"
//        prefs.clearValues
//        prefs.proyek = etTxtDashboard.text.toString()
//        etTxtDashboard.text=(prefs.proyek.toString())

//        etTxtDashboard.text="ini direksi halo  "+SharedPrefManager.getInstance(this).user.nama+"posisi "+SharedPrefManager.getInstance(this).user.posisi
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
                }

            })
    }

    override fun onStart() {
        super.onStart()

        cek_report()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        when(SharedPrefManager.getInstance(this).user.posisi){
            "Pengawas" -> pengawas()
//            "PPK" -> Toast.makeText(applicationContext, "Selamat datang "+SharedPrefManager.getInstance(this).user.nama, Toast.LENGTH_LONG).show()
            "KPA" -> kpa()
            "PPSPM" -> ppspm()
            "KASUBDIT" -> kpa()
            else -> print("Gagal mendeteksi posisi")
        }

    }

    private fun pengawas() {
        val intent = Intent(applicationContext, MainPengawasActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun kpa() {
        val intent = Intent(applicationContext, MainKpaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun ppspm() {
        val intent = Intent(applicationContext, MainPpspmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    object PreferenceHelper {

        val PROYEK = "proyek"

        fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

        inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
            val editMe = edit()
            operation(editMe)
            editMe.apply()
        }

        var SharedPreferences.proyek
            get() = getString(PROYEK, "")
            set(value) {
                editMe {
                    it.putString(PROYEK, value)
                }
            }

        var SharedPreferences.clearValues
            get() = { }
            set(value) {
                editMe {
                    it.clear()
                }
            }
    }
}
