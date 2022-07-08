package com.mark.badmintonpeer.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mark.badmintonpeer.data.Chat
import com.mark.badmintonpeer.data.ChatItem
import com.mark.badmintonpeer.databinding.ChatroomChatOtherBinding
import com.mark.badmintonpeer.databinding.ChatroomChatUserBinding
import com.mark.badmintonpeer.util.TimeCalculator

class ChatroomChatAdapter : ListAdapter<ChatItem, RecyclerView.ViewHolder>(DiffCallback) {

    class UserSideViewHolder(private var binding: ChatroomChatUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(chat: Chat) {
                binding.chat = chat
                binding.textChatOwnerTime.text = TimeCalculator.getTime(chat.createdTime.time)
                binding.executePendingBindings()
            }
        }

    class OtherSideViewHolder(private var binding: ChatroomChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chat = chat
            binding.textChatOtherTime.text = TimeCalculator.getTime(chat.createdTime.time)
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.createdTime == newItem.createdTime
        }

        private const val ITEM_VIEW_TYPE_USERSIDE = 0x00
        private const val ITEM_VIEW_TYPE_OTHERSIDE = 0x01

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_USERSIDE -> UserSideViewHolder(
                ChatroomChatUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_OTHERSIDE -> OtherSideViewHolder(
                ChatroomChatOtherBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when (holder) {
           is UserSideViewHolder -> {
               holder.bind((getItem(position) as ChatItem.UserSide).chat)
           }
           is OtherSideViewHolder -> {
               holder.bind((getItem(position) as ChatItem.OtherSide).chat)
           }
       }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatItem.UserSide -> ITEM_VIEW_TYPE_USERSIDE
            is ChatItem.OtherSide -> ITEM_VIEW_TYPE_OTHERSIDE
        }

    }
}