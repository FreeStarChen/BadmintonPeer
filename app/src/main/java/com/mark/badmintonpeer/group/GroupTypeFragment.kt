package com.mark.badmintonpeer.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.databinding.GroupTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import timber.log.Timber

class GroupTypeFragment(private val type: String) : Fragment() {

    /**
     * Lazily initialize our [GroupTypeViewModel].
     */
    private val viewModel by viewModels<GroupTypeViewModel> {getVmFactory(type)}

    companion object {
        fun newInstance(type: String): GroupTypeFragment {
            val fragment = GroupTypeFragment(type)
            val args = Bundle()
            args.putString("type",type)
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

        val binding = GroupTypeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.recyclerViewGroupType.adapter = GroupTypeAdapter(
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
            (binding.recyclerViewGroupType.adapter as GroupTypeAdapter).submitList(it)
            Timber.d("groups=${viewModel.groups.value}")
        }

       return binding.root
    }

}