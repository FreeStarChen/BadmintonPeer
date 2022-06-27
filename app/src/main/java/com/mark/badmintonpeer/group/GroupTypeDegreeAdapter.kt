package com.mark.badmintonpeer.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.databinding.GroupTypeItemDegreeBinding

class GroupTypeDegreeAdapter :
    ListAdapter<String, GroupTypeDegreeAdapter.DegreeViewHolder>(DiffCallback) {


    class DegreeViewHolder(private var binding: GroupTypeItemDegreeBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(degree: String) {
                binding.degree = degree
                binding.executePendingBindings()
            }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DegreeViewHolder {
        return DegreeViewHolder(
            GroupTypeItemDegreeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DegreeViewHolder, position: Int) {
        val degree = getItem(position)
        holder.bind(degree)
    }
}