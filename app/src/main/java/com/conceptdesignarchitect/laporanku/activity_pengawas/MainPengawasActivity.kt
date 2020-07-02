package com.conceptdesignarchitect.laporanku.activity_pengawas

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesignarchitect.laporanku.ProfilActivity
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.activity_ppk.LoginActivity
import com.conceptdesignarchitect.laporanku.activity_ppk.MainPPKActivity
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main_pengawas.*


class MainPengawasActivity : AppCompatActivity() {
    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pengawas)

        idUserEmail.text = SharedPrefManager.getInstance(this).user.email

        val prefs = MainPPKActivity.PreferenceHelper.customPreference(this, CUSTOM_PREF_NAME)
//        prefs.proyek = etTxtDashboard.text.toString()

//        etTxtDashboard.text=(prefs.password.toString())
//        val preff = SharePrefProyek.getInstance(this)
//        etTxtDashboard2.text="ini pelaksana halo "+SharedPrefManager.getInstance(this).user.nama+"posisi "+SharedPrefManager.getInstance(this).user.posisi

        id_tombol_report.setOnClickListener {
            intent = Intent(applicationContext, PekerjaanActivity::class.java)
            startActivity(intent)
        }
        id_btn_profil.setOnClickListener {
            intent = Intent(applicationContext, ProfilActivity::class.java)
            startActivity(intent)


        }
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}
