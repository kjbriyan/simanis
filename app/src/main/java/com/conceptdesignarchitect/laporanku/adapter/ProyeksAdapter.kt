package com.conceptdesignarchitect.laporanku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.conceptdesignarchitect.laporanku.R
import com.conceptdesignarchitect.laporanku.activity_pengawas.PekerjaanActivity
import com.conceptdesignarchitect.laporanku.models.DataProyekItem
import kotlinx.android.synthetic.main.list_detail_pekerjaan.view.*

class ProyeksAdapter(val data:List<DataProyekItem>?, val itemClickListener: PekerjaanActivity):
    RecyclerView.Adapter<ProyeksAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProyeksAdapter.MyHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.list_detail_pekerjaan, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ProyeksAdapter.MyHolder, position: Int) {
        holder.bind(data?.get(position), itemClickListener)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(get: DataProyekItem?, clickListener: PekerjaanActivity){
            itemView.id_txt_nama_proyek.text = get?.proyek
            val rincian ="Pekerjaan ${get?.totalBobot} % | ${get?.tersisa} hari tersisa"
            itemView.id_txt_selisih.text = rincian
            itemView.tv_vol_kontrak.text = get?.tersisa.toString()

            itemView.setOnClickListener { clickListener.onItemClicked(get) }
            //            itemView.setOnClickListener {
            //                itemView.let {
            //                    val Toast =Toast.makeText(context, "Proyek : "+get!!.proyek, Toast.LENGTH_SHORT)
            //                    Toast.show()
            //                }
            //            }
        }
    }
}
