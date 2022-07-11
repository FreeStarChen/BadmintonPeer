package com.mark.badmintonpeer.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.databinding.ChatroomChatFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory

class ChatroomChatFragment : Fragment() {

    private val viewModel by viewModels<ChatroomChatViewModel> {
        getVmFactory(
            ChatroomChatFragmentArgs.fromBundle(
                requireArguments()
            ).chatroomKey
        )
    }

    lateinit var binding: ChatroomChatFragmentBinding

    companion object {
        fun newInstance() = ChatroomChatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatroomChatFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = ChatroomChatAdapter()

        binding.recyclerViewChat.adapter = adapter

        viewModel.chatItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.imageChatSend.setOnClickListener {
            if (binding.editTextChatInputMessage.text.toString() != "") {
                val content = binding.editTextChatInputMessage.text.toString()
                viewModel.sendMessageResult(content)
                viewModel.addChatroomMessageAndTimeResult()
            }
            binding.editTextChatInputMessage.text.clear()
        }

        binding.imageChatBack.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToChatroomFragment())
        }

        viewModel.liveChatItem.observe(viewLifecycleOwner) {
            val chats = viewModel.chatToChatItem(it)
            adapter.submitList(chats)
            binding.recyclerViewChat.smoothScrollToPosition(chats.size)
        }

        return binding.root
    }


}