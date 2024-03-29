package com.example.recyclerviewlistadapter

import androidx.recyclerview.widget.DiffUtil

class MyDiffCallback : DiffUtil.ItemCallback<Monster>() {
    override fun areItemsTheSame(oldItem: Monster, newItem: Monster): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Monster, newItem: Monster): Boolean {
        return oldItem == newItem
    }
}