package com.conceptdesignarchitect.laporanku.activity_pengawas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.adapter.PekerjaanAdapter
import com.conceptdesignarchitect.laporanku.api.Api
import com.conceptdesignarchitect.laporanku.api.Initretrofit
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.*
import com.conceptdesignarchitect.laporanku.models.PekMinglalu.ResponseDetailPekerjaanlalu
import kotlinx.android.synthetic.main.activity_realisasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealisasiActivity : AppCompatActivity() {

    private val sectionArray = ArrayList<String>()
    private val subKerjaArray = ArrayList<String>()
    var title: String = ""
    var volLalu =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi)

        sectionArray.add("Pilih Section / Bagian")
        lihatbobotminggu()
        lihatSection()

        id_btn_kirim.setOnClickListener { kirimlaporan() }
    }


    fun onItemClicked(get: DataDetailpekerjaanItem?) {
        Toast.makeText(this@RealisasiActivity, "klik " + get?.bobot, Toast.LENGTH_LONG).show()
        title = get?.uraianPekerjaan.toString()
        val minggu = intent.getStringExtra("minggu")
        alertdialog(
            get?.proyek.toString(),
            get?.fkIdMinggu.toString(), minggu, get?.idkerja.toString(),
            get?.volumeDetail.toString(), get?.volume.toString(), get?.bobot.toString()
        )
        getVolMinglalu(get?.idkerja.toString(),get?.fkIdMinggu.toString())
    }

    fun alertdialog(
        proyek: String,
        idming: String, mingke: String, idurai: String,
        edvol: String, vsd: String, bobot: String
    ) {
        val builder = AlertDialog.Builder(this@RealisasiActivity)
        val view = layoutInflater.inflate(R.layout.edit_item_pekerjaan, null)
        builder.setView(view)
//        Toast.makeText(this@RealisasiActivity, "klik " + bobot, Toast.LENGTH_LONG).show()
        view.findViewById<TextView>(R.id.tv_title).text = title
        view.findViewById<TextView>(R.id.tv_vol_sdmingini).text = vsd
        view.findViewById<TextView>(R.id.tv_vol_minglalu).text = volLalu


        val etVOlume = view.findViewById<EditText>(R.id.et_vol_mingini)
        etVOlume.setText(edvol)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Save") { dialog, which ->
            // Do something when user press the positive button
            val datvol = etVOlume.text.toString()
            Log.d(
                "etanol",
                proyek + " idming " + idming + " - " + mingke + " - " + idurai + " - " + datvol + " - " + vsd + " - " + bobot
            )
            val init = Initretrofit().getInstance()
                .simpanvolume(proyek, " ", idming, mingke, idurai, datvol, vsd, bobot)
            init.enqueue(object : Callback<KirimResponse> {
                override fun onFailure(call: Call<KirimResponse>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "faild network" + t.message + "-" + t.cause,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<KirimResponse>,
                    response: Response<KirimResponse>
                ) {
                    val res = response.body()
                    if (response.body()?.status == "Berhasil Tersimpan") {
                        Toast.makeText(
                            applicationContext,
                            "status " + res?.status,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(applicationContext, "faild sent", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            })

        }
        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(applicationContext, "You cancelled the dialog.", Toast.LENGTH_SHORT)
                .show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun getVolMinglalu(idKerja : String, idMinggu : String){
        val init = Initretrofit().getInstance().detailLalu(idKerja,idMinggu)
        init.enqueue(object : Callback<ResponseDetailPekerjaanlalu>{
            override fun onFailure(call: Call<ResponseDetailPekerjaanlalu>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "faild network" + t.message + "-" + t.cause,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<ResponseDetailPekerjaanlalu>,
                response: Response<ResponseDetailPekerjaanlalu>
            ) {
                val res = response.body()
                if (response.body()?.status == "Berhasil") {
//                    Toast.makeText(
//                        applicationContext,
//                        "status " + res?.status,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    volLalu = res?.dataDetailpekerjaanlalu?.get(0)?.volumeAkhir.toString()

                } else {
                    Toast.makeText(applicationContext, "faild get data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }


    private fun lihatbobotminggu() {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.lihatbobotminggu(
            intent.getStringExtra("proyek"),
            intent.getStringExtra("idminggu"),
            intent.getStringExtra("minggu")
        )
            .enqueue(object : Callback<BobotmingguResponse> {
                override fun onFailure(call: Call<BobotmingguResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<BobotmingguResponse>,
                    response: Response<BobotmingguResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if (response.isSuccessful) {
                        val list = response.body()?.dataTotalBobot as List<DataTotalBobotItem>?
                        for (i in list!!.indices) {
                            idtotal_minggulalu.text = list[i].mingguLalu.toString()
                            idtotal_mingguini.text = list[i].mingguIni.toString()
                            idtotal_sdmingguini.text = list[i].sdMingguIni.toString()
                        }
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

    private fun lihatSection() {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.lihatsection(intent.getStringExtra("proyek"))
            .enqueue(object : Callback<SectionResponse> {
                override fun onFailure(call: Call<SectionResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                @Suppress("UNCHECKED_CAST")
                override fun onResponse(
                    call: Call<SectionResponse>,
                    response: Response<SectionResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if (response.isSuccessful) {
                        val list = response.body()?.dataSection as List<DataSectionItem>?
                        for (i in list!!.indices) {
                            sectionArray.add(list[i].section.toString())
                        }
                        val spinnerAdapter = ArrayAdapter(
                            this@RealisasiActivity,
                            android.R.layout.simple_spinner_item,
                            sectionArray
                        )
                        idspinsection.adapter = spinnerAdapter
                        idspinsection.onItemSelectedListener = object :

                            AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (sectionArray[position] !== "Pilih Section / Bagian") {
                                    lihatSubPekerjaan(sectionArray[position])
                                    Toast.makeText(
                                        applicationContext,
                                        "." + sectionArray[position],
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        }
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

    private fun lihatSubPekerjaan(section: String) {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.lihatsubpekerjaan(section, intent.getStringExtra("proyek"))
            .enqueue(object : Callback<SubpekerjaanResponse> {
                override fun onFailure(call: Call<SubpekerjaanResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                @Suppress("UNCHECKED_CAST")
                override fun onResponse(
                    call: Call<SubpekerjaanResponse>,
                    response: Response<SubpekerjaanResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if (response.isSuccessful) {

                        subKerjaArray.clear()
                        subKerjaArray.add("Pilih sub pekerjaan")

                        val list2 = response.body()?.dataSubpekerjaan as List<DataSubpekerjaanItem>?
                        for (i in list2!!.indices) {
                            subKerjaArray.add(list2[i].pekerjaan.toString())
                        }
                        val spinnerAdapter2 = ArrayAdapter(
                            this@RealisasiActivity,
                            android.R.layout.simple_spinner_item,
                            subKerjaArray
                        )
                        idspinsubpekerjaan.adapter = spinnerAdapter2
                        idspinsubpekerjaan.onItemSelectedListener = object :

                            AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (subKerjaArray[position] !== "Pilih sub pekerjaan") {
                                    lihatDetailPekerjaan(section, subKerjaArray[position])
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "." + subKerjaArray[position],
//                                        Toast.LENGTH_LONG
//                                    ).show()

                                }
                            }

                        }
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

    private fun lihatDetailPekerjaan(section: String, pekerjaan: String) {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.lihatdetailpekerjaan(
            intent.getStringExtra("proyek"),
            section,
            intent.getStringExtra("idminggu"),
            pekerjaan
        )
            .enqueue(object : Callback<DetailpekerjaanResponse> {
                override fun onFailure(call: Call<DetailpekerjaanResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<DetailpekerjaanResponse>,
                    response: Response<DetailpekerjaanResponse>
                ) {
                    Log.i("Response", response.body().toString());
                    if (response.isSuccessful) {
                        if (response.body()?.status == "Berhasil") {
                            @Suppress("UNCHECKED_CAST")
                            idRvRealisasi.adapter = PekerjaanAdapter(
                                response.body()?.dataDetailpekerjaan as List<DataDetailpekerjaanItem>?,
                                this@RealisasiActivity
                            )

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Gagal mendapat response",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Maaf terjadi kesalahan..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
    }

    private fun kirimlaporan() {
        RetrofitClient.instance.Kirimlaporan(
            intent.getStringExtra("proyek"),
            intent.getStringExtra("idminggu")
        )
            .enqueue(object : Callback<KirimResponse> {
                override fun onFailure(call: Call<KirimResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<KirimResponse>,
                    response: Response<KirimResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "" + response.body()?.status,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Maaf terjadi Kesalahan..",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })
    }
}


