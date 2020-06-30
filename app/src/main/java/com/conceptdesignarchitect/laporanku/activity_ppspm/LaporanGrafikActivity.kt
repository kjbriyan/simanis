package com.conceptdesignarchitect.laporanku.activity_ppspm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesignarchitect.laporanku.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_laporan_grafik.*

class LaporanGrafikActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_grafik)

//        val proyek = intent.getStringExtra("proyek")
//        val bobot_pekerjaan = intent.getStringExtra("total")?.toString()
//
//        val total_bobot_sudah_dikerjakan = bobot_pekerjaan!!.toFloat()
//        val total_bobot_belum_dikerjakan = 100 -  bobot_pekerjaan.toFloat()

        val listPie = ArrayList<PieEntry>()
        val listColors = ArrayList<Int>()
        listPie.add(PieEntry(50F, "Dikerjakan"))
        listColors.add(resources.getColor(R.color.colorAccent))
        listPie.add(PieEntry(50F, "Belum Dikerjakan"))
        listColors.add(resources.getColor(R.color.kuning))

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors

        val pieData = PieData(pieDataSet)
        idpie_kontruksi.data = pieData
        pieData.setValueFormatter(PercentFormatter(idpie_kontruksi))

        idpie_kontruksi.setUsePercentValues(true)
        idpie_kontruksi.isDrawHoleEnabled = false
        idpie_kontruksi.setDrawEntryLabels(false)
        idpie_kontruksi.holeRadius = 15F
        idpie_kontruksi.description.isEnabled = false
        idpie_kontruksi.isDrawHoleEnabled = true
        idpie_kontruksi.setUsePercentValues(true)
        idpie_kontruksi.setEntryLabelColor(R.color.contentTextColor)
        idpie_kontruksi.animateY(900, Easing.EaseInOutQuad)

        val listPie2 = ArrayList<PieEntry>()
        val listColors2 = ArrayList<Int>()
        listPie2.add(PieEntry(52F, "Terbayar"))
        listColors2.add(resources.getColor(R.color.mera))
        listPie2.add(PieEntry(48F, "Belum Terbayar"))
        listColors2.add(resources.getColor(R.color.hijo))

        val pieDataSet2 = PieDataSet(listPie2, "")
        pieDataSet2.colors = listColors2

        val pieData2 = PieData(pieDataSet2)
        idpie_budgeting.data = pieData2
        pieData2.setValueFormatter(PercentFormatter(idpie_budgeting))

        idpie_budgeting.setUsePercentValues(true)
        idpie_budgeting.isDrawHoleEnabled = false
        idpie_budgeting.setDrawEntryLabels(false)
        idpie_budgeting.holeRadius = 15F
        idpie_budgeting.description.isEnabled = false
        idpie_budgeting.isDrawHoleEnabled = true
        idpie_budgeting.setUsePercentValues(true)
        idpie_budgeting.isUsePercentValuesEnabled
        idpie_budgeting.setEntryLabelColor(R.color.contentTextColor)
        idpie_budgeting.animateY(900, Easing.EaseInOutQuad)
    }
}
