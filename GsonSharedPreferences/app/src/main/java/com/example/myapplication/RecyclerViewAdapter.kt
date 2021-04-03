package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var dataSet: ArrayList<Monster> = arrayListOf()

    fun removeData(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(dataSet, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    fun addData(name: String, race: Race, level: Int, stats: List<Int>, encount: Boolean) {
        dataSet.add(Monster(name, race, level, stats, encount))
        notifyItemInserted(dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Monster) {
            binding.tvName.text = "Name: ${data.name}"
            binding.tvRace.text = "Race: ${data.race}"
            binding.tvLevel.text = "Level: ${data.level}"
            binding.tvStats.text = "HP: ${data.stats[0]} / MP: ${data.stats[1]} / Exp: ${data.stats[2]}"
            binding.tvEncount.text = "Encounted: ${data.encount}"

            binding.vhLayout.setOnClickListener {
                Snackbar.make(it, "Item $layoutPosition touched!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}