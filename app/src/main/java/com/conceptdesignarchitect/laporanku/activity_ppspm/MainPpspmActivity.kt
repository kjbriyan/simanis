package com.conceptdesignarchitect.laporanku.activity_ppspm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.conceptdesignarchitect.laporanku.R
import kotlinx.android.synthetic.main.activity_main_ppspm.*

class MainPpspmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ppspm)

        id_btn_report_budget.setOnClickListener {
            intent = Intent(applicationContext, BudgetingActivity::class.java)
            startActivity(intent)
        }
    }
}
