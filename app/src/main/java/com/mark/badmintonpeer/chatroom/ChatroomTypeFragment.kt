package com.mark.badmintonpeer.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.databinding.ChatroomTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import timber.log.Timber

class ChatroomTypeFragment : Fragment() {

    private val viewModel by viewModels<ChatroomTypeViewModel> { getVmFactory(getType()) }

    companion object {
        fun newInstance(type: String): ChatroomTypeFragment {
            val fragment = ChatroomTypeFragment()
            val args = Bundle()
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }
    }

    private fun getType(): String {
        return requireArguments().getString("type", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ChatroomTypeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.recyclerViewChatroomType.adapter = ChatroomTypeAdapter(
            ChatroomTypeAdapter.OnClickListener {
                viewModel.navigateToChatroomDetail(it)
            }
        )

        viewModel.navigateToChatroomDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.navigateToChatroomChatFragment(it)
                )
                viewModel.onChatroomDetailNavigated()
            }
        }

        viewModel.chatroom.observe(viewLifecycleOwner) {
            (binding.recyclerViewChatroomType.adapter as ChatroomTypeAdapter).submitList(it)
            Timber.d("chatroom=${viewModel.chatroom.value}")
        }

        binding.layoutSwipeRefreshChatroomType.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner) {
            it?.let {
                binding.layoutSwipeRefreshChatroomType.isRefreshing = it
            }
        }

        return binding.root
    }
}
