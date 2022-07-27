package com.mark.badmintonpeer.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.chatroom.ChatroomTypeFragment
import com.mark.badmintonpeer.chatroom.ChatroomTypeViewModel
import com.mark.badmintonpeer.databinding.ChatroomTypeFragmentBinding
import com.mark.badmintonpeer.databinding.RecordTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.group.GroupTypeAdapter
import timber.log.Timber

class RecordTypeFragment : Fragment() {

    private val viewModel by viewModels<RecordTypeViewModel> {getVmFactory(getType())}

    private lateinit var binding: RecordTypeFragmentBinding

    lateinit var recordViewModel: RecordViewModel

    companion object {
        fun newInstance(type: String): RecordTypeFragment {
            val fragment = RecordTypeFragment()
            val args = Bundle()
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }
    }

    fun getType(): String {
        return requireArguments().getString("type","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = RecordTypeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerViewRecordType.adapter = GroupTypeAdapter(
            GroupTypeAdapter.OnClickListener {
                viewModel.navigateToGroupDetail(it)
            }
        )

        viewModel.navigateToGroupDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToGroupDetailFragment(it))
                viewModel.onGroupDetailNavigated()
            }
        }

        viewModel.groups.observe(viewLifecycleOwner) {
            (binding.recyclerViewRecordType.adapter as GroupTypeAdapter).submitList(it)
        }

        recordViewModel = ViewModelProvider(requireParentFragment()).get(RecordViewModel::class.java)

        recordViewModel.type.observe(viewLifecycleOwner) {
            Timber.d("recordViewModel.type =${recordViewModel.type.value}")
            Timber.d("viewModel.type =${viewModel.type}")
           when (viewModel.type) {
               "過往揪團" -> viewModel.getRecordOfCreatedGroupResult(it)
               "過往參團" -> viewModel.getRecordOfJoinGroupResult(it)
           }
        }





        return binding.root
    }

}