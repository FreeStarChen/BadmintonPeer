package com.mark.badmintonpeer.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.databinding.ChatroomGroupChatFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import timber.log.Timber

class ChatroomGroupChatFragment : Fragment() {

    private val viewModel by viewModels<ChatroomGroupChatViewModel> {
        getVmFactory(
            ChatroomGroupChatFragmentArgs.fromBundle(
                requireArguments()
            ).groupKey
        )
    }

    lateinit var binding: ChatroomGroupChatFragmentBinding

    companion object {
        fun newInstance() = ChatroomGroupChatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChatroomGroupChatFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = ChatroomChatAdapter()

        binding.recyclerViewGroupChat.adapter = adapter

        viewModel.chatItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.imageGroupChatSend.setOnClickListener {
            if (binding.editTextGroupChatInputMessage.text.toString() != "") {
                val content = binding.editTextGroupChatInputMessage.text.toString()
                viewModel.sendMessageResult(content)
                viewModel.addChatroomMessageAndTimeResult()
            }
            binding.editTextGroupChatInputMessage.text.clear()
        }

        binding.imageGroupChatBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.checkChatroom.observe(viewLifecycleOwner) {
            if (it == null) {
                viewModel.addChatroomResult()
                viewModel.getGroupChatroomResult()
            } else {
                if (MainApplication.instance.isLiveDataDesign()) {
                    viewModel.getLiveChatsResult()
                } else {
                    viewModel.getChatsResult()
                }
            }
        }

        viewModel.observeChatItem.observe(viewLifecycleOwner) {
            Timber.d("viewModel.liveChats.observe, it=$it")
            if (it) {

                viewModel.liveChatItem.observe(viewLifecycleOwner) { ListChat ->
                    val chats = viewModel.chatToChatItem(ListChat)
                    adapter.submitList(chats)
                    binding.recyclerViewGroupChat.smoothScrollToPosition(chats.size)
                }
            }
        }

        return binding.root
    }
}
