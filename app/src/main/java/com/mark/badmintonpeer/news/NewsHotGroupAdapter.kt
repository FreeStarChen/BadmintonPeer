package com.mark.badmintonpeer.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.databinding.NewsHotGroupItemBinding
import com.mark.badmintonpeer.util.TimeCalculator

class NewsHotGroupAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Group, NewsHotGroupAdapter.GroupViewHolder>(DiffCallback) {
    class GroupViewHolder(private var binding: NewsHotGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(group: Group) {
            binding.group = group
            binding.textDateNews.text = TimeCalculator.getDateAndWeek(group.date.time).replace("週","")
            binding.textStartTimeNews.text = TimeCalculator.getTime(group.startTime.time)
            binding.textEndTimeNews.text = TimeCalculator.getTime(group.endTime.time)
            binding.textPriceNews.text = "$${group.price}"
            binding.textCityNews.text = group.address.substring(0,3)

            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            NewsHotGroupItemBinding.inflate(
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