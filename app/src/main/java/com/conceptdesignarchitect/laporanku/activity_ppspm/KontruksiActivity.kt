package com.conceptdesignarchitect.laporanku.activity_ppspm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.conceptdesignarchitect.laporanku.R
import kotlinx.android.synthetic.main.activity_kontruksi.*

class KontruksiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontruksi)

        id_btn_pelaksana.setOnClickListener {
            val intent = Intent(this, BudgetingProyekActivity::class.java)
            intent.putExtra("kategori", "pelaksana")
            startActivity(intent)
        }
    }
}
