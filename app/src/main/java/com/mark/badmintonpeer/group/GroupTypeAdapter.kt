package com.mark.badmintonpeer.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.databinding.GroupTypeItemFragmentBinding

class GroupTypeAdapter(private val onClickListener: OnClickListener) : ListAdapter<Group, GroupTypeAdapter.GroupViewHolder>(DiffCallback) {
    class GroupViewHolder(private var binding: GroupTypeItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: Group) {
            binding.group = group
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            GroupTypeItemFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = getItem(position)
        group?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(group)
            }
            holder.bind(group)
        }
    }

    class OnClickListener(val clickListener: (group: Group) -> Unit) {
        fun onClick(group: Group) = clickListener(group)
    }
}