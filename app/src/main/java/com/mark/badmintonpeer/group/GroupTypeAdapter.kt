package com.mark.badmintonpeer.group

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.databinding.GroupTypeItemFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class GroupTypeAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Group, GroupTypeAdapter.GroupViewHolder>(DiffCallback) {
    class GroupViewHolder(private var binding: GroupTypeItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(group: Group) {
            binding.group = group
            binding.textDate.text = getDate(group.date.time)
            binding.textStartTime.text = getTime(group.startTime.time)
            binding.textEndTime.text = getTime(group.endTime.time)
            binding.textPrice.text = "$${group.price}"
            binding.executePendingBindings()
        }

        @SuppressLint("SimpleDateFormat")
        fun getTime(time: Long): String {
            return if (android.os.Build.VERSION.SDK_INT >= 24) {
                SimpleDateFormat("HH:mm").format(time)
            } else {
                val tms = Calendar.getInstance()
                tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                        tms.get(Calendar.MONTH).toString() + "/" +
                        tms.get(Calendar.YEAR).toString() + " " +
                        tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                        tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                        tms.get(Calendar.MINUTE).toString() + ":" +
                        tms.get(Calendar.SECOND).toString()
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun getDate(time: Long): String {
            return if (android.os.Build.VERSION.SDK_INT >= 24) {
                SimpleDateFormat("YYYY/MM/dd EE").format(time)
            } else {
                val tms = Calendar.getInstance()
                tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                        tms.get(Calendar.MONTH).toString() + "/" +
                        tms.get(Calendar.YEAR).toString() + " " +
                        tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                        tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                        tms.get(Calendar.MINUTE).toString() + ":" +
                        tms.get(Calendar.SECOND).toString()
            }
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