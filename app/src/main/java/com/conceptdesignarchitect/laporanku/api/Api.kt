package com.conceptdesignarchitect.laporanku.api

import com.conceptdesignarchitect.laporanku.models.*
import com.conceptdesignarchitect.laporanku.models.PekMinglalu.ResponseDetailPekerjaanlalu
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("pekerjaan")
    fun pilihProyek(
        @Field("id") id:Int
    ):Call <ProyekResponse>

    @FormUrlEncoded
    @POST("minggu")
    fun mingguKe(
        @Field("proyek") proyek:String
    ):Call <MingguResponse>

    @FormUrlEncoded
    @POST("section")
    fun lihatsection(
        @Field("proyek") proyek:String
    ):Call <SectionResponse>

    @FormUrlEncoded
    @POST("subpekerjaan")
    fun lihatsubpekerjaan(
        @Field("section") section:String,
        @Field("proyek") proyek:String
    ):Call <SubpekerjaanResponse>

    @FormUrlEncoded
    @POST("bobotminggu")
    fun lihatbobotminggu(
        @Field("proyek") proyek:String,
        @Field("idminggu") idminggu:String,
        @Field("minggu") minggu:String

    ):Call <BobotmingguResponse>

    @FormUrlEncoded
    @POST("detailpekerjaan")
    fun lihatdetailpekerjaan(
        @Field("proyek") proyek:String,
        @Field("section") section:String,
        @Field("idminggu") idminggu:String,
        @Field("pekerjaan") pekerjaan:String
    ):Call <DetailpekerjaanResponse>

    @FormUrlEncoded
    @POST("kirimlaporan")
    fun Kirimlaporan(
        @Field("proyek") proyek:String,
        @Field("idminggu") idminggu:String
    ):Call <KirimResponse>

    @FormUrlEncoded
    @POST("kirimuang")
    fun Kirimuang(
        @Field("id") proyek:String,
        @Field("kategori") kategori:String,
//        @Field("rincian") rincian:String,
        @Field("uang") nilai:String
    ):Call <KirimResponse>

    @FormUrlEncoded
    @POST("validasippk")
    fun Validasilaporan(
        @Field("proyek") proyek:String,
        @Field("idminggu") idminggu:String
    ):Call <KirimResponse>

    @FormUrlEncoded
    @POST("cekvalidasi")
    fun Cekvalidasi(
        @Field("id") id:Int,
        @Field("proyek") proyek:String,
        @Field("idminggu") idminggu:String
    ):Call <KirimResponse>

    @FormUrlEncoded
    @POST("ceknotif")
    fun ceknotif(
        @Field("id") id: Int
    ):Call <CeknotifResponse>

    @FormUrlEncoded
    @POST("lihatlaporan")
    fun lihatlaporan(
        @Field("id") id: Int
    ):Call <LihatlaporanResponse>

    @FormUrlEncoded
    @POST("simpan_data_volume")
    fun simpanvolume(
        @Field("dataproyek") dataproyek: String,
        @Field("dataketerangan") keterangan : String,
        @Field("idminggu") idminggu: String,
        @Field("datamingguke") mingguke: String,
        @Field("iduraian") iduraian : String,
        @Field("datavolume") volume: String,
        @Field("datavolumeasli") volumeasli: String,
        @Field("databobotasli") bobotasli : String
    ):Call <KirimResponse>

    @FormUrlEncoded
    @POST("Lihatminggulalu")
    fun detailLalu(
        @Field("datavolumeasli") volumeasli: String,
        @Field("databobotasli") bobotasli : String
    ):Call <ResponseDetailPekerjaanlalu>
}