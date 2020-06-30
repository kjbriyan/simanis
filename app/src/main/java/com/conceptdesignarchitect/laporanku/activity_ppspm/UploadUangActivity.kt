package com.conceptdesignarchitect.laporanku.activity_ppspm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.api.RetrofitClient
import com.conceptdesignarchitect.laporanku.models.KirimResponse
import kotlinx.android.synthetic.main.activity_upload_uang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadUangActivity : AppCompatActivity() {
    var nilai = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_uang)

        id_btn_kirimuang.setOnClickListener {
//            if (idtext_nilai != null){
                nilai = idtext_nilai.text.toString()
                kirimuang()
//            }else{
//                Toast.makeText(applicationContext, "Silahkan isi nilai uang terlebih dahulu", Toast.LENGTH_SHORT).show()
//            }

        }
    }

    private fun kirimuang() {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        RetrofitClient.instance.Kirimuang(intent.getStringExtra("proyek"), intent.getStringExtra("kategori"), nilai)
            .enqueue(object : Callback<KirimResponse> {
                override fun onFailure(call: Call<KirimResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<KirimResponse>,
                    response: Response<KirimResponse>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(applicationContext, ""+response.body()?.status, Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "Maaf terjadi Kesalahan.." , Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
