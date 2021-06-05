package com.example.recyclerviewassets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewassets.databinding.ListItemBinding

class MyRecyclerViewAdapter(private val dataset: CoronaMed) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class MyViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CoronaMedItem) {
            with(binding) {
                tvName.text = data.mEDINSTNM
                tvType.text = data.dISTRCTDIVDTLS
                tvPhonenum.text = data.eMGCCENTERTELNO
                tvAddress.text = data.rEFINEROADNMADDR
            }
        }
    }
}