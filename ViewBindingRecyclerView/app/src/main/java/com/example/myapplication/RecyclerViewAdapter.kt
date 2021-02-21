package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemBinding

class RecyclerViewAdapter(private val dataSet: ArrayList<List<String>>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
//        return ViewHolder(view)
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

//    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
//        private val tvMain: TextView = view.tv_main
//        private val tvSub: TextView = view.tv_sub
//
//        fun bind(data:List<String>) {
//            tvMain.text = data[0]
//            tvSub.text = data[1]
//        }
//    }
    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:List<String>) {
            binding.tvMain.text = data[0]
            binding.tvSub.text = data[1]
        }
    }
}