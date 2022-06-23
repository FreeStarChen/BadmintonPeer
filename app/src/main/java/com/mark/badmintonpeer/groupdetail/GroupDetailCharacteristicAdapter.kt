package com.mark.badmintonpeer.groupdetail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.databinding.GroupDetailCharacteristicBinding
import com.mark.badmintonpeer.util.CharacteristicDrawable

class GroupDetailCharacteristicAdapter :
    ListAdapter<String, GroupDetailCharacteristicAdapter.CharacteristicViewHolder>(DiffCallback) {


    class CharacteristicViewHolder(private var binding: GroupDetailCharacteristicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characteristic: String) {
            binding.characteristic = characteristic
            CharacteristicDrawable.getDrawable(characteristic)
                ?.let { binding.imageCharacteristic.setImageResource(it) }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicViewHolder {
        return CharacteristicViewHolder(
            GroupDetailCharacteristicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacteristicViewHolder, position: Int) {
        val degree = getItem(position)
        holder.bind(degree)
    }
}