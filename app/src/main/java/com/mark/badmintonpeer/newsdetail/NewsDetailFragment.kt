package com.mark.badmintonpeer.newsdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.NewsDetailFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.groupdetail.GroupDetailFragment
import com.mark.badmintonpeer.groupdetail.GroupDetailFragmentArgs
import com.mark.badmintonpeer.groupdetail.GroupDetailViewModel

class NewsDetailFragment : Fragment() {

    private val viewModel by viewModels<NewsDetailViewModel> {
        getVmFactory(
            NewsDetailFragmentArgs.fromBundle(
                requireArguments()
            ).newKey
        )
    }

    private lateinit var binding: NewsDetailFragmentBinding

    companion object {
        fun newInstance() = NewsDetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = NewsDetailFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.imageNewsDetailBack.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToNewsFragment())
        }

        return binding.root
    }


}