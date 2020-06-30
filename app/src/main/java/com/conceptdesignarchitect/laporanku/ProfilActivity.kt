package com.conceptdesignarchitect.laporanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.session.SharedPrefManager

import kotlinx.android.synthetic.main.activity_profil.*

class ProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val name = SharedPrefManager.getInstance(this).user.nama
        val posisi = SharedPrefManager.getInstance(this).user.posisi
        val email = SharedPrefManager.getInstance(this).user.email
        val nip = SharedPrefManager.getInstance(this).user.nip

        et_nama.setText(""+name)
        et_nip.setText(""+nip)
        et_posisi.setText(""+posisi)
        et_email.setText(""+email)

        back.setNavigationOnClickListener {
            finish()
        }
    }
}
