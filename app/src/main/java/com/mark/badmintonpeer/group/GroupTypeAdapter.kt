package com.mark.badmintonpeer.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.util.TimeCalculator
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.databinding.GroupTypeItemBinding

class GroupTypeAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Group, GroupTypeAdapter.GroupViewHolder>(DiffCallback) {
    class GroupViewHolder(private var binding: GroupTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(group: Group) {
            binding.group = group
            binding.textDate.text = TimeCalculator.getDate(group.date.time)
            binding.textStartTime.text = TimeCalculator.getTime(group.startTime.time)
            binding.textEndTime.text = TimeCalculator.getTime(group.endTime.time)
            binding.textPrice.text = "$${group.price}"

            val adapter = GroupTypeDegreeAdapter()
            binding.recyclerViewDegree.adapter = adapter
            adapter.submitList(group.degree)
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            GroupTypeItemBinding.inflate(
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