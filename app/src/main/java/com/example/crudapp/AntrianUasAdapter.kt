package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.AntrianUas
import kotlinx.android.synthetic.main.adapter_antrian_uas.view.*

class AntrianUasAdapter (private val allAntrianUas: ArrayList<AntrianUas>, private val listener: OnAdapterListener) : RecyclerView.Adapter<AntrianUasAdapter.AntrianViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntrianViewHolder {
        return AntrianViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_antrian_uas, parent, false)
        )
    }

    override fun getItemCount() = allAntrianUas.size

    override fun onBindViewHolder(holder: AntrianViewHolder, position: Int) {
        val antrianm = allAntrianUas[position]
        holder.view.text_nama.text = antrianm.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(antrianm)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(antrianm)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(antrianm)
        }
    }

    class AntrianViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<AntrianUas>) {
        allAntrianUas.clear()
        allAntrianUas.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(antrianuas: AntrianUas)
        fun onDelete(antrianuas: AntrianUas)
        fun onUpdate(antrianuas: AntrianUas)
    }
}