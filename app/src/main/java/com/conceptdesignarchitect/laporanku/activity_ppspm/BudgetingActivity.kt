package com.conceptdesignarchitect.laporanku.activity_ppspm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.conceptdesignarchitect.laporanku.R
import kotlinx.android.synthetic.main.activity_budgeting.*

class BudgetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budgeting)

        id_btn_nonkontruksi.setOnClickListener {
            intent = Intent(applicationContext, NonKontruksiActivity::class.java)
            startActivity(intent)
        }

        id_btn_konstruksi.setOnClickListener {
            intent = Intent(applicationContext, KontruksiActivity::class.java)
            startActivity(intent)
        }

        id_btn_grap.setOnClickListener {
            intent = Intent(applicationContext, LaporanGrafikActivity::class.java)
            startActivity(intent)
        }
    }
}
