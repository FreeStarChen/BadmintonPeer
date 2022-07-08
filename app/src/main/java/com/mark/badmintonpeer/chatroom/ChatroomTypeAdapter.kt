package com.mark.badmintonpeer.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.Chatroom
import com.mark.badmintonpeer.databinding.ChatroomTypeItemBinding
import com.mark.badmintonpeer.util.TimeCalculator


class ChatroomTypeAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Chatroom, ChatroomTypeAdapter.ChatroomViewHolder>(DiffCallback) {
    class ChatroomViewHolder(private var binding: ChatroomTypeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(chatroom: Chatroom) {
                binding.chatroom = chatroom
                binding.textLastTalkTime.text = chatroom.lastTalkTime?.let {
                    TimeCalculator.getDate(
                        it.time)
                }
            }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Chatroom>() {
        override fun areItemsTheSame(oldItem: Chatroom, newItem: Chatroom): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Chatroom, newItem: Chatroom): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatroomViewHolder {
        return ChatroomViewHolder(
            ChatroomTypeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatroomViewHolder, position: Int) {
        val chatroom = getItem(position)
        chatroom?.let {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(chatroom)
            }
            holder.bind(chatroom)
        }
    }

    class OnClickListener(val clickListener: (chatroom: Chatroom) -> Unit) {
        fun onClick(chatroom: Chatroom) = clickListener(chatroom)
    }
}